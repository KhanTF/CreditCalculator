package ru.rager.credit.presentation.ui.calculation.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.adapters.recyclerview.PaymentListAdapter
import ru.rager.credit.presentation.databinding.FragmentCalculationPaymentsBinding
import ru.rager.credit.presentation.ui.base.BaseFragment
import ru.rager.credit.presentation.ui.calculation.CalculationViewModel
import ru.rager.credit.presentation.util.itemdecorations.LinearSpaceItemDecoration

class CalculationPaymentsFragment : BaseFragment<CalculationViewModel, FragmentCalculationPaymentsBinding>() {

    companion object {
        fun getInstance() = CalculationPaymentsFragment()
    }

    private val adapter = PaymentListAdapter()

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentCalculationPaymentsBinding {
        return FragmentCalculationPaymentsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.paymentList.also {
            adapter.creditPaymentList = viewModel.creditPaymentList
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(LinearSpaceItemDecoration(start = R.dimen.dp_16, top = R.dimen.dp_16, space = R.dimen.dp_8))
        }
    }

}
