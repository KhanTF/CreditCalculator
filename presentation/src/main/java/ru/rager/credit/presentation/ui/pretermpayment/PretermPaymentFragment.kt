package ru.rager.credit.presentation.ui.pretermpayment

import android.os.Bundle
import android.view.View
import ru.rager.credit.presentation.databinding.FragmentPretermPaymentBinding
import ru.rager.credit.presentation.ui.base.BaseFragment
import ru.rager.credit.presentation.util.DecimalTextWatcher
import ru.rager.credit.presentation.util.binding.converters.EnumBindingConverter
import ru.rager.credit.presentation.util.getArrayAdapter

class PretermPaymentFragment : BaseFragment<PretermPaymentViewModel, FragmentPretermPaymentBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.pretermPaymentSum.addTextChangedListener(DecimalTextWatcher())
        viewModel.pretermTypeList.observe { pretermTypeList ->
            val data = pretermTypeList.map { EnumBindingConverter.mapPretermTypeToString(requireContext(), it) }
            binding.pretermType.setAdapter(getArrayAdapter(requireContext(), data))
        }
        viewModel.pretermPeriodValueList.observe { pretermPeriodTemplateList ->
            val data = pretermPeriodTemplateList.map { EnumBindingConverter.mapPeriodValueToEveryString(requireContext(), it) }
            binding.pretermPeriod.setAdapter(getArrayAdapter(requireContext(), data))
        }
    }

}