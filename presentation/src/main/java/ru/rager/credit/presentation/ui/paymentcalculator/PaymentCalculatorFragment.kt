package ru.rager.credit.presentation.ui.paymentcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.rager.credit.presentation.databinding.FragmentPaymentCalculatorBinding
import ru.rager.credit.presentation.ui.base.BaseIndependentFragment

class PaymentCalculatorFragment : BaseIndependentFragment<PaymentCalculatorViewModel, FragmentPaymentCalculatorBinding>() {

    companion object {
        fun getInstance() = PaymentCalculatorFragment()
    }

    override val viewModelClass = PaymentCalculatorViewModel::class.java

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentPaymentCalculatorBinding {
        return FragmentPaymentCalculatorBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}