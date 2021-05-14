package ru.rager.credit.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<ViewModel : BaseViewModel, Binding : ViewDataBinding> : DaggerFragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected val viewModel: ViewModel
        get() = requireNotNull(viewModelInternal)

    protected val binding: Binding
        get() = requireNotNull(bindingInternal)

    protected abstract val viewModelClass: Class<ViewModel>

    private var viewModelInternal: ViewModel? = null

    private var bindingInternal: Binding? = null

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        viewModelInternal = ViewModelProvider(this, viewModelFactory).get(viewModelClass)
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

    protected abstract fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): Binding

    protected inline fun <T> LiveData<T>.observe(crossinline action: (T) -> Unit) {
        observe(this@BaseFragment, { action(it) })
    }

}