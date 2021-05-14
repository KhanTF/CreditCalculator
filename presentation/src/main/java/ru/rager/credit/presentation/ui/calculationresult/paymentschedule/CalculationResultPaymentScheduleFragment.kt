package ru.rager.credit.presentation.ui.calculationresult.paymentschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rager.credit.presentation.databinding.FragmentCalculationResultPaymentScheduleBinding
import ru.rager.credit.presentation.ui.base.BaseFragment
import ru.rager.credit.presentation.ui.calculationresult.CalculationResultViewModel

class CalculationResultPaymentScheduleFragment : BaseFragment<CalculationResultViewModel, FragmentCalculationResultPaymentScheduleBinding>() {

    companion object {
        fun getInstance() = CalculationResultPaymentScheduleFragment()
    }

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentCalculationResultPaymentScheduleBinding {
        return FragmentCalculationResultPaymentScheduleBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}
