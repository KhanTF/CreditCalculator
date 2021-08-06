package ru.rager.credit.presentation.ui.calculation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rager.credit.domain.entity.CreditDateCalculationEntity
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.common.recyclerview.CalculationListAdapter
import ru.rager.credit.presentation.databinding.FragmentCalculationPaymentsBinding
import ru.rager.credit.presentation.ui.base.BaseFragment
import ru.rager.credit.presentation.util.itemdecorations.LinearSpaceItemDecoration

class CalculationPaymentsFragment : BaseFragment<CalculationViewModel, FragmentCalculationPaymentsBinding>() {

    companion object {
        fun getInstance() = CalculationPaymentsFragment()
    }

    private val adapter = CalculationListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.paymentList.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(LinearSpaceItemDecoration(start = R.dimen.dp_16, top = R.dimen.dp_16, space = R.dimen.dp_8))
        }
        viewModel.dateCalculationList.observe {
            adapter.data = it.filterIsInstance(CreditDateCalculationEntity.SchedulePaymentCreditDateCalculationEntity::class.java)
        }
    }

}
