package ru.rager.credit.presentation.ui.creditcalculator

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rager.credit.domain.entity.CreditEarlyPaymentEntity
import ru.rager.credit.domain.entity.enums.TermTemplateType
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.common.recyclerview.EarlyPaymentListAdapter
import ru.rager.credit.presentation.common.recyclerview.TermTemplateListAdapter
import ru.rager.credit.presentation.databinding.FragmentCreditCalculatorBinding
import ru.rager.credit.presentation.ui.base.BaseFragment
import ru.rager.credit.presentation.util.itemdecorations.LinearSpaceItemDecoration

class CreditCalculatorFragment : BaseFragment<CreditCalculatorViewModel, FragmentCreditCalculatorBinding>() {

    private val creditTermTemplateAdapter = TermTemplateListAdapter(object : TermTemplateListAdapter.TermTemplateListener {
        override fun onSelectTermTemplate(value: TermTemplateType) {
            viewModel.onSelectTermTemplate(value)
        }
    })

    private val creditEarlyPaymentListAdapter = EarlyPaymentListAdapter(object : EarlyPaymentListAdapter.Listener {
        override fun onDelete(index: Int) {
            viewModel.onDeleteEarlyPayment(index)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.creditTermTemplateList.adapter = creditTermTemplateAdapter
        binding.creditTermTemplateList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.creditTermTemplateList.addItemDecoration(
            LinearSpaceItemDecoration(
                start = R.dimen.dp_16,
                end = R.dimen.dp_16,
                space = R.dimen.dp_4,
                top = R.dimen.dp_0,
                bottom = R.dimen.dp_0,
                orientation = LinearLayoutManager.HORIZONTAL
            )
        )
        binding.earlyPaymentList.adapter = creditEarlyPaymentListAdapter
        binding.earlyPaymentList.layoutManager = LinearLayoutManager(context)
        binding.earlyPaymentList.addItemDecoration(
            LinearSpaceItemDecoration(
                start = R.dimen.dp_16,
                end = R.dimen.dp_16,
                space = R.dimen.dp_8,
                top = R.dimen.dp_16,
                bottom = R.dimen.dp_8,
                orientation = LinearLayoutManager.VERTICAL
            )
        )
        viewModel.creditTermTemplateList.observe {
            creditTermTemplateAdapter.data = it
        }
        viewModel.creditEarlyPaymentList.observe {
            creditEarlyPaymentListAdapter.data = it
        }
    }

}