package ru.rager.credit.presentation.ui.calculationresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.ui.base.BaseIndependentFragment
import ru.rager.credit.presentation.databinding.FragmentCalculationResultBinding
import ru.rager.credit.presentation.model.CalculationParameters

class CalculationResultFragment : BaseIndependentFragment<CalculationResultViewModel, FragmentCalculationResultBinding>() {

    companion object {
        private const val KEY_CALCULATION_PARAMETERS = "KEY_CALCULATION_PARAMETERS"

        fun getInstance(calculationParameters: CalculationParameters) = CalculationResultFragment().apply {
            val bundle = Bundle()
            bundle.putParcelable(KEY_CALCULATION_PARAMETERS, calculationParameters)
            arguments = bundle
        }
    }

    val calculationParameters: CalculationParameters
        get() = requireNotNull(requireArguments().getParcelable(KEY_CALCULATION_PARAMETERS))

    private val adapter by lazy { CalculationResultPagerAdapter(this) }

    override val viewModelClass: Class<CalculationResultViewModel>
        get() = CalculationResultViewModel::class.java

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentCalculationResultBinding {
        return FragmentCalculationResultBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pagerAdapter = adapter
        binding.viewModel = viewModel
        R.array.tab_names
    }

}
