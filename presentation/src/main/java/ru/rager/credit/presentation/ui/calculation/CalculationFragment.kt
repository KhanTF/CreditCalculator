package ru.rager.credit.presentation.ui.calculation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.presentation.adapters.pagers.CalculationPagerAdapter
import ru.rager.credit.presentation.databinding.FragmentCalculationBinding
import ru.rager.credit.presentation.model.dto.CalculationEntityDto
import ru.rager.credit.presentation.ui.base.BaseIndependentFragment

class CalculationFragment : BaseIndependentFragment<CalculationViewModel, FragmentCalculationBinding>() {

    companion object {
        private const val KEY_CREDIT_CALCULATION = "KEY_CREDIT_CALCULATION"

        fun getInstance(creditCalculationEntity: CreditCalculationEntity) = CalculationFragment().apply {
            val bundle = Bundle()
            bundle.putParcelable(KEY_CREDIT_CALCULATION, CalculationEntityDto(creditCalculationEntity))
            arguments = bundle
        }

    }

    private val adapter by lazy { CalculationPagerAdapter(this) }

    fun getCreditCalculationArgument() = requireNotNull(requireArguments().getParcelable<CalculationEntityDto>(KEY_CREDIT_CALCULATION)?.get())

    override val viewModelClass: Class<CalculationViewModel>
        get() = CalculationViewModel::class.java

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentCalculationBinding {
        return FragmentCalculationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pagerAdapter = adapter
        binding.viewModel = viewModel
    }

}
