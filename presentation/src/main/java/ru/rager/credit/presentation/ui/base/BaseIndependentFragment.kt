package ru.rager.credit.presentation.ui.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

abstract class BaseIndependentFragment<ViewModel : BaseViewModel, Binding : ViewDataBinding> : BaseFragment<ViewModel, Binding>() {

    @Inject
    lateinit var viewModelProvider: Provider<ViewModel>

    protected abstract val viewModelClass: Class<ViewModel>

    @Suppress("UNCHECKED_CAST")
    private val viewModelProviderFactory = object : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            return viewModelProvider.get() as T
        }
    }

    override fun getViewModelInstance(): ViewModel {
        return ViewModelProvider(this, viewModelProviderFactory).get(viewModelClass)
    }
}