package ru.rager.credit.presentation.ui.percentcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.databinding.FragmentPercentCalculatorBinding
import ru.rager.credit.presentation.ui.base.BaseIndependentFragment
import ru.rager.credit.presentation.ui.base.events.Event

class PercentCalculatorFragment : BaseIndependentFragment<PercentCalculatorViewModel, FragmentPercentCalculatorBinding>() {

    companion object {
        fun getInstance() = PercentCalculatorFragment()
    }

    override val viewModelClass = PercentCalculatorViewModel::class.java

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentPercentCalculatorBinding {
        return FragmentPercentCalculatorBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onHandleEvent(event: Event) {
        super.onHandleEvent(event)
    }

}