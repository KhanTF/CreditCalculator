package ru.rager.credit.presentation.ui.createearlypayment

import android.os.Bundle
import android.view.View
import ru.rager.credit.presentation.databinding.FragmentCreateEarlyPaymentBinding
import ru.rager.credit.presentation.ui.base.BaseFragment

class CreateEarlyPaymentFragment : BaseFragment<CreateEarlyPaymentViewModel, FragmentCreateEarlyPaymentBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}