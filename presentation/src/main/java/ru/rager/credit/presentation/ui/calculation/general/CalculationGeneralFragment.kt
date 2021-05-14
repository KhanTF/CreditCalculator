package ru.rager.credit.presentation.ui.calculation.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rager.credit.presentation.databinding.FragmentCalculationGeneralBinding
import ru.rager.credit.presentation.ui.base.BaseFragment
import ru.rager.credit.presentation.ui.calculation.CalculationViewModel

class CalculationGeneralFragment : BaseFragment<CalculationViewModel, FragmentCalculationGeneralBinding>() {

    companion object {
        fun getInstance() = CalculationGeneralFragment()
    }

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentCalculationGeneralBinding {
        return FragmentCalculationGeneralBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}
