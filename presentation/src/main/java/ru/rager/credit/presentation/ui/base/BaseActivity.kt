package ru.rager.credit.presentation.ui.base

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject
import javax.inject.Provider

abstract class BaseActivity<ViewModel : BaseViewModel, Binding : ViewDataBinding> : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var viewModelProvider: Provider<ViewModel>

    protected abstract val viewModelClass: Class<ViewModel>

    protected abstract val navigator: Navigator

    protected val viewModel: ViewModel
        get() = requireNotNull(viewModelInternal)

    protected val binding: Binding
        get() = requireNotNull(bindingInternal)

    private var viewModelInternal: ViewModel? = null

    private var bindingInternal: Binding? = null

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModelInternal = getViewModelInstance()
        val binding = getViewDataBindingInstance()
        binding.lifecycleOwner = this
        bindingInternal = binding
        setContentView(binding.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingInternal = null
    }

    internal open fun getViewModelInstance() = ViewModelProvider(this, object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            return viewModelProvider.get() as T
        }
    }).get(viewModelClass)

    internal abstract fun getViewDataBindingInstance(): Binding

}