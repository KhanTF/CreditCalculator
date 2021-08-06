package ru.rager.credit.presentation.ui.creditcalculator

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rager.credit.domain.entity.PeriodValueEntity
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.common.recyclerview.PretermListAdapter
import ru.rager.credit.presentation.common.recyclerview.TermTemplateListAdapter
import ru.rager.credit.presentation.databinding.FragmentCreditCalculatorBinding
import ru.rager.credit.presentation.ui.base.BaseFragment
import ru.rager.credit.presentation.util.binding.converters.EnumBindingConverter
import ru.rager.credit.presentation.util.getArrayAdapter
import ru.rager.credit.presentation.util.itemdecorations.LinearSpaceItemDecoration

class CreditCalculatorFragment : BaseFragment<CreditCalculatorViewModel, FragmentCreditCalculatorBinding>() {

    private val creditTermTemplateAdapter = TermTemplateListAdapter(object : TermTemplateListAdapter.Listener {
        override fun onSelectTermTemplate(value: PeriodValueEntity) {
            viewModel.onSelectTermTemplate(value)
        }
    })

    private val creditEarlyPaymentListAdapter = PretermListAdapter(object : PretermListAdapter.Listener {
        override fun onEdit(index: Int) {
            viewModel.onEditPretermPayment(index)
        }

        override fun onDelete(index: Int) {
            viewModel.onDeleteEarlyPayment(index)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.toolbar.inflateMenu(R.menu.menu_credit_calculator)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.preterm_payment -> {
                    viewModel.onCreatePretermPayment()
                    true
                }
                R.id.rate_change -> {
                    viewModel.onCreateRateChange()
                    true
                }
                else -> false
            }
        }
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
        binding.pretermPaymentList.adapter = creditEarlyPaymentListAdapter
        binding.pretermPaymentList.layoutManager = LinearLayoutManager(context)
        binding.pretermPaymentList.addItemDecoration(
            LinearSpaceItemDecoration(
                start = R.dimen.dp_16,
                end = R.dimen.dp_16,
                space = R.dimen.dp_8,
                top = R.dimen.dp_16,
                bottom = R.dimen.dp_16,
                orientation = LinearLayoutManager.VERTICAL
            )
        )
        viewModel.termTemplateList.observe {
            creditTermTemplateAdapter.data = it
        }
        viewModel.pretermPaymentList.observe {
            creditEarlyPaymentListAdapter.data = it
        }
        viewModel.rateTypeList.observe { rateTypeList ->
            val data = rateTypeList.map { EnumBindingConverter.mapRateTypeToString(requireContext(), it) }
            binding.rateType.setAdapter(getArrayAdapter(requireContext(), data))
        }
        viewModel.ratePeriodList.observe { ratePeriodList ->
            val data = ratePeriodList.map { EnumBindingConverter.mapPeriodTypeToRateTypeString(requireContext(), it) }
            binding.ratePeriodType.setAdapter(getArrayAdapter(requireContext(), data))
        }
        viewModel.paymentPeriodList.observe { paymentPeriodList ->
            val data = paymentPeriodList.map { EnumBindingConverter.mapPeriodTypeToPaymentTypeString(requireContext(), it) }
            binding.paymentPeriodType.setAdapter(getArrayAdapter(requireContext(), data))
        }
        viewModel.isPretermVisible.observe {
            val item = binding.toolbar.menu.findItem(R.id.preterm_payment)
            item.isVisible = it
        }
        viewModel.isRateChangeVisible.observe {
            val item = binding.toolbar.menu.findItem(R.id.rate_change)
            item.isVisible = it
        }
    }

}