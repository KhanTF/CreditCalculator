package ru.rager.credit.presentation.ui.calculation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.adapters.pagers.CalculationPagerAdapter
import ru.rager.credit.presentation.databinding.FragmentCalculationBinding
import ru.rager.credit.presentation.dialogs.DeleteCalculationDialogFragment
import ru.rager.credit.presentation.dialogs.SaveCalculationDialogFragment
import ru.rager.credit.presentation.ui.base.BaseIndependentFragment

class CalculationFragment : BaseIndependentFragment<CalculationViewModel, FragmentCalculationBinding>(), SaveCalculationDialogFragment.Callback, DeleteCalculationDialogFragment.Callback {

    companion object {
        private const val TAG_SAVE_CALCULATION = "TAG_SAVE_CALCULATION"
        private const val TAG_DELETE_CALCULATION = "TAG_DELETE_CALCULATION"
        private const val KEY_PARAMETERS = "KEY_PARAMETERS"

        fun getInstance(parameters: CalculationViewModel.Parameters) = CalculationFragment().apply {
            val bundle = Bundle()
            bundle.putParcelable(KEY_PARAMETERS, parameters)
            arguments = bundle
        }

    }

    val parameters by lazy { requireNotNull(requireArguments().getParcelable<CalculationViewModel.Parameters>(KEY_PARAMETERS)) }

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

}
