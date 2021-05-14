package ru.rager.credit.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Provider

abstract class BaseFragment<ViewModel : BaseViewModel, Binding : ViewDataBinding> : DaggerFragment() {

    protected val viewModel: ViewModel
        get() = requireNotNull(viewModelInternal)

    protected val binding: Binding
        get() = requireNotNull(bindingInternal)

    private var viewModelInternal: ViewModel? = null

    private var bindingInternal: Binding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModelInternal = getViewModelInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = getViewDataBindingInstance(inflater, container)
        binding.lifecycleOwner = viewLifecycleOwner
        bindingInternal = binding
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInternal = null
    }

    protected inline fun <T> LiveData<T>.observe(crossinline action: (T) -> Unit) {
        observe(this@BaseFragment, { action(it) })
    }

    internal open fun getViewModelInstance() = getParentViewModel()

    internal abstract fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): Binding

    @Suppress("UNCHECKED_CAST")
    private fun getParentViewModel(): ViewModel {
        val parentFragment = parentFragment as? BaseFragment<*, *>
        val parentActivity = activity as? BaseActivity<*, *>
        val parentFragmentViewModel = parentFragment?.getViewModelInstance() as? ViewModel
        val prentActivityViewModel = parentActivity?.getViewModelInstance() as? ViewModel
        return parentFragmentViewModel ?: prentActivityViewModel ?: throw IllegalArgumentException("ViewModel not found")
    }

}