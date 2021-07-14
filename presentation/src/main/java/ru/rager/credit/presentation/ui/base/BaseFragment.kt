package ru.rager.credit.presentation.ui.base

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import ru.rager.credit.presentation.R
import java.lang.reflect.ParameterizedType
import javax.inject.Inject
import javax.inject.Provider

abstract class BaseFragment<ViewModel : BaseViewModel, Binding : ViewDataBinding> : DaggerFragment(), FragmentResultListener {

    companion object {
        private const val KEY_RESULT = "KEY_RESULT"
        private const val KEY_REQUEST_KEYS = "KEY_REQUEST_KEYS"
    }

    @Inject
    lateinit var viewModelProvider: Provider<ViewModel>

    protected val binding: Binding
        get() = requireNotNull(bindingInternal)

    private var bindingInternal: Binding? = null

    protected lateinit var viewModel: ViewModel

    private val requestKeys = HashSet<String>()

    protected open val navController: NavController
        get() = findNavController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreRequestKeys(savedInstanceState)
        viewModel = getViewModelInstance()
    }

    override fun onStart() {
        super.onStart()
        setupFragmentResultListener()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = getViewDataBindingInstance(inflater, container)
        binding.lifecycleOwner = viewLifecycleOwner
        bindingInternal = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewModelEvent.observe(this::onHandleEvent)
    }

    override fun onStop() {
        super.onStop()
        clearFragmentResultListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        saveFragmentResultListener(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInternal = null
    }

    override fun onFragmentResult(requestKey: String, result: Bundle) {
        val payload = result.getParcelable<ViewModelResult>(KEY_RESULT) ?: throw IllegalArgumentException("Unexpected payload")
        viewModel.onHandleResult(requestKey, payload)
        clearFragmentResult(requestKey)
    }

    protected open fun onHandleBaseEvent(viewModelEvent: ViewModelEvent.BaseEvent) {
        when (viewModelEvent) {
            is ErrorEvent -> Snackbar.make(requireView(), R.string.error_unknown, Snackbar.LENGTH_SHORT).show()
            is SetResultEvent -> setFragmentResult(requireArguments().getString(ResultNavDirections.KEY_REQUEST_KEY, null), viewModelEvent.payload)
        }
    }

    protected open fun onHandleNavigationEvent(viewModelEvent: ViewModelEvent.NavigationEvent) {
        when (viewModelEvent) {
            is OpenDirectionEvent -> {
                val direction = viewModelEvent.direction
                if (direction is ResultNavDirections) {
                    addFragmentResultListener(direction.requestKey)
                }
                navController.navigate(direction)
            }
            is CloseDirectionEvent -> close()
        }
    }

    protected open fun onHandleExtendedEvent(viewModelEvent: ViewModelEvent.ExtendedEvent) {}

    private fun onHandleEvent(viewModelEvent: ViewModelEvent) {
        when (viewModelEvent) {
            is ViewModelEvent.BaseEvent -> onHandleBaseEvent(viewModelEvent)
            is ViewModelEvent.NavigationEvent -> onHandleNavigationEvent(viewModelEvent)
            is ViewModelEvent.ExtendedEvent -> onHandleExtendedEvent(viewModelEvent)
        }
    }

    protected open fun close() {
        if (!navController.popBackStack()) {
            requireActivity().finish()
        }
    }

    protected inline fun <T> LiveData<T>.observe(crossinline action: (T) -> Unit) {
        observe(viewLifecycleOwner, { action(it) })
    }

    private fun setupFragmentResultListener() {
        for (requestKey in requestKeys) {
            addFragmentResultListener(requestKey)
        }
    }

    private fun restoreRequestKeys(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            requestKeys.addAll(savedInstanceState.getStringArrayList(KEY_REQUEST_KEYS).orEmpty())
        }
    }

    private fun clearFragmentResultListener() {
        for (requestKey in requestKeys) {
            parentFragmentManager.clearFragmentResultListener(requestKey)
        }
    }

    private fun saveFragmentResultListener(outState: Bundle) {
        outState.putStringArrayList(KEY_REQUEST_KEYS, ArrayList(requestKeys))
    }

    private fun addFragmentResultListener(requestKey: String) {
        parentFragmentManager.setFragmentResultListener(requestKey, this, this)
        requestKeys.add(requestKey)
    }

    private fun clearFragmentResult(requestKey: String) {
        parentFragmentManager.clearFragmentResult(requestKey)
    }

    private fun <K : Parcelable> setFragmentResult(key: String?, result: K) {
        val bundle = Bundle()
        bundle.putParcelable(KEY_RESULT, result)
        parentFragmentManager.setFragmentResult(key ?: return, bundle)
    }

    @Suppress("unchecked_cast")
    private fun getViewModelInstance(): ViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
                return viewModelProvider.get() as T
            }
        }).get(getViewModelClass())
    }

    @Suppress("unchecked_cast")
    protected fun getViewDataBindingInstance(layoutInflater: LayoutInflater, container: ViewGroup?): Binding {
        val method = getBindingClass().getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        return method.invoke(null, layoutInflater, container, false) as Binding
    }

    @Suppress("unchecked_cast")
    private fun getViewModelClass(): Class<ViewModel> {
        val bindingType = this::class.java.genericSuperclass as ParameterizedType
        return bindingType.actualTypeArguments[0] as Class<ViewModel>
    }

    @Suppress("unchecked_cast")
    private fun getBindingClass(): Class<Binding> {
        val bindingType = this::class.java.genericSuperclass as ParameterizedType
        return bindingType.actualTypeArguments[1] as Class<Binding>
    }


}