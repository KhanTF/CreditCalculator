package ru.rager.credit.presentation.ui.earlypayment

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.rager.credit.presentation.databinding.FragmentEarlyPaymentBinding
import ru.rager.credit.presentation.ui.base.BaseIndependentFragment

class EarlyPaymentFragment : BaseIndependentFragment<EarlyPaymentViewModel, FragmentEarlyPaymentBinding>() {

    override val viewModelClass: Class<EarlyPaymentViewModel> = EarlyPaymentViewModel::class.java

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentEarlyPaymentBinding {
        return FragmentEarlyPaymentBinding.inflate(inflater, container, false)
    }

}