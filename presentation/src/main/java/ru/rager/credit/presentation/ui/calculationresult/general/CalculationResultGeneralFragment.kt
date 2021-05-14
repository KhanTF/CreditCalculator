package ru.rager.credit.presentation.ui.calculationresult.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rager.credit.presentation.databinding.FragmentCalculationResultGeneralBinding
import ru.rager.credit.presentation.ui.base.BaseFragment
import ru.rager.credit.presentation.ui.calculationresult.CalculationResultViewModel

class CalculationResultGeneralFragment : BaseFragment<CalculationResultViewModel, FragmentCalculationResultGeneralBinding>() {

    companion object {
        fun getInstance() = CalculationResultGeneralFragment()
    }

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentCalculationResultGeneralBinding {
        return FragmentCalculationResultGeneralBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}
