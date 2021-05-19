package ru.rager.credit.presentation.ui.calculation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.SavedCreditCalculationParameterEntity
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.adapters.pagers.CalculationPagerAdapter
import ru.rager.credit.presentation.databinding.FragmentCalculationBinding
import ru.rager.credit.presentation.dialogs.SaveCalculationDialogFragment
import ru.rager.credit.presentation.model.dto.CalculationEntityDto
import ru.rager.credit.presentation.model.dto.CalculationParameterEntityDto
import ru.rager.credit.presentation.ui.base.BaseIndependentFragment

class CalculationFragment : BaseIndependentFragment<CalculationViewModel, FragmentCalculationBinding>(), SaveCalculationDialogFragment.Callback {

    companion object {
        private const val TAG_SAVE_CALCULATION = "TAG_SAVE_CALCULATION"
        private const val KEY_CREDIT_CALCULATION = "KEY_CREDIT_CALCULATION"

        fun getInstance(calculationParameterEntity: CreditCalculationParameterEntity) = CalculationFragment().apply {
            val bundle = Bundle()
            bundle.putParcelable(KEY_CREDIT_CALCULATION, CalculationParameterEntityDto(calculationParameterEntity))
            arguments = bundle
        }

    }

    private val adapter by lazy { CalculationPagerAdapter(this) }

    fun getCreditCalculationParameterArgument() = requireNotNull(requireArguments().getParcelable<CalculationParameterEntityDto>(KEY_CREDIT_CALCULATION)?.get())

    override val viewModelClass: Class<CalculationViewModel>
        get() = CalculationViewModel::class.java

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentCalculationBinding {
        return FragmentCalculationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pagerAdapter = adapter
        binding.viewModel = viewModel
        binding.toolbar.apply {
            val saveMenuItem = menu.findItem(R.id.save)
            saveMenuItem.isVisible = !viewModel.creditCalculationIsSaved
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.save -> {
                        SaveCalculationDialogFragment
                            .getInstance()
                            .show(childFragmentManager, TAG_SAVE_CALCULATION)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onSave(name: String) {
        viewModel.onSaveCalculation(name)
    }

}
