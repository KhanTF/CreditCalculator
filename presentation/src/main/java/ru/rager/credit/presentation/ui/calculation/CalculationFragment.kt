package ru.rager.credit.presentation.ui.calculation

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.adapters.pagers.CalculationPagerAdapter
import ru.rager.credit.presentation.databinding.FragmentCalculationBinding
import ru.rager.credit.presentation.dialogs.DeleteCalculationDialogFragment
import ru.rager.credit.presentation.dialogs.SaveCalculationDialogFragment
import ru.rager.credit.presentation.model.dto.CalculationParameterEntityDto
import ru.rager.credit.presentation.ui.base.BaseIndependentFragment

class CalculationFragment : BaseIndependentFragment<CalculationViewModel, FragmentCalculationBinding>(), SaveCalculationDialogFragment.Callback, DeleteCalculationDialogFragment.Callback {

    companion object {
        private const val TAG_SAVE_CALCULATION = "TAG_SAVE_CALCULATION"
        private const val TAG_DELETE_CALCULATION = "TAG_DELETE_CALCULATION"
        private const val KEY_PARAMETERS = "KEY_PARAMETERS"

        fun getInstance(
            id: Long?,
            name: String?,
            creditRateType: CreditRateType,
            creditSum: Double,
            creditRate: Double,
            creditTerm: Int
        ) = CalculationFragment().apply {
            val parameters = Parameters(id, name, creditRateType, creditSum, creditRate, creditTerm)
            val bundle = Bundle()
            bundle.putParcelable(KEY_PARAMETERS, parameters)
            arguments = bundle
        }

    }

    val parameters by lazy { requireNotNull(requireArguments().getParcelable<Parameters>(KEY_PARAMETERS)) }

    private val adapter by lazy { CalculationPagerAdapter(this) }

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
            val deleteMenuItem = menu.findItem(R.id.delete)
            saveMenuItem.isVisible = viewModel.creditCalculationId == null
            deleteMenuItem.isVisible = viewModel.creditCalculationId != null
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.save -> {
                        SaveCalculationDialogFragment
                            .getInstance()
                            .show(childFragmentManager, TAG_SAVE_CALCULATION)
                        true
                    }
                    R.id.delete -> {
                        DeleteCalculationDialogFragment
                            .getInstance(viewModel.creditCalculationName.orEmpty())
                            .show(childFragmentManager, TAG_DELETE_CALCULATION)
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

    override fun onDelete() {
        viewModel.onDelete()
    }

    @Parcelize
    data class Parameters(
        val id: Long?,
        val name: String?,
        val creditRateType: CreditRateType,
        val creditSum: Double,
        val creditRate: Double,
        val creditTerm: Int
    ) : Parcelable

}
