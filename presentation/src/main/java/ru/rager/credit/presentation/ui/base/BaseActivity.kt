package ru.rager.credit.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ru.rager.credit.presentation.R
import java.lang.reflect.ParameterizedType
import javax.inject.Inject
import javax.inject.Provider

abstract class BaseActivity<ViewModel : BaseViewModel, Binding : ViewDataBinding> : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelProvider: Provider<ViewModel>

    lateinit var viewModel: ViewModel

    protected abstract val navController: NavController

    protected val binding: Binding
        get() = requireNotNull(bindingInternal)

    private var bindingInternal: Binding? = null

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = getViewDataBindingInstance().also { it.lifecycleOwner = this }
        bindingInternal = binding
        setContentView(binding.root)
        viewModel = getViewModelInstance()
        viewModel.viewModelEvent.observe(this::onHandleEvent)
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingInternal = null
    }

    private fun onHandleEvent(viewModelEvent: ViewModelEvent) {
        when (viewModelEvent) {
            is ViewModelEvent.BaseEvent -> onHandleBaseEvent(viewModelEvent)
            is ViewModelEvent.NavigationEvent -> onHandleNavigationEvent(viewModelEvent)
            is ViewModelEvent.ExtendedEvent -> onHandleExtendedEvent(viewModelEvent)
        }
    }

    protected open fun onHandleBaseEvent(viewModelEvent: ViewModelEvent.BaseEvent) {
        when (viewModelEvent) {
            is ErrorEvent -> Snackbar.make(findViewById(android.R.id.content), R.string.error_unknown, Snackbar.LENGTH_SHORT).show()
            is SetResultEvent -> throw IllegalArgumentException("Unsupported event for activity")
        }
    }

    protected open fun onHandleNavigationEvent(viewModelEvent: ViewModelEvent.NavigationEvent) {
        when (viewModelEvent) {
            is OpenDirectionEvent -> navController.navigate(viewModelEvent.direction)
            is CloseDirectionEvent -> close()
        }
    }

    protected open fun onHandleExtendedEvent(viewModelEvent: ViewModelEvent.ExtendedEvent) {}

    protected open fun close() {
        if (!navController.popBackStack()) {
            finish()
        }
    }

    protected inline fun <T> LiveData<T>.observe(crossinline action: (T) -> Unit) {
        observe(this@BaseActivity, { action(it) })
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
    protected fun getViewDataBindingInstance(): Binding {
        val method = getBindingClass().getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as Binding
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