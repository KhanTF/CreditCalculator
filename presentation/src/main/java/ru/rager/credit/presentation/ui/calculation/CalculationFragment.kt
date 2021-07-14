package ru.rager.credit.presentation.ui.calculation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayoutMediator
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.databinding.FragmentCalculationBinding
import ru.rager.credit.presentation.ui.base.BaseFragment
import ru.rager.credit.presentation.ui.base.ViewModelEvent
import ru.rager.credit.presentation.ui.dialog.simple.SimpleDialogResult

class CalculationFragment : BaseFragment<CalculationViewModel, FragmentCalculationBinding>(), Toolbar.OnMenuItemClickListener {

    companion object {
        private const val REQUEST_KEY_DELETE_CALCULATION_CONFIRMATION = "ru.rager.credit.presentation.ui.calculation.CalculationViewModel.REQUEST_KEY_DELETE_CALCULATION_CONFIRMATION"
        private const val REQUEST_KEY_SAVE_CALCULATION = "ru.rager.credit.presentation.ui.calculation.CalculationViewModel.REQUEST_KEY_SAVE_CALCULATION"
    }

    private val calculationPagerAdapter by lazy { CalculationPagerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupView()
        setupViewModel()
    }

    override fun onHandleExtendedEvent(viewModelEvent: ViewModelEvent.ExtendedEvent) {
        when (viewModelEvent) {
            is CalculationViewModel.OpenDeleteCalculationConfirmationViewModelEvent -> navController.navigate(
                CalculationFragmentDirections.toSimpleDialog(
                    viewModelEvent.calculationName,
                    getString(R.string.credit_delete_calculation_confirmation, viewModelEvent.calculationName),
                    getString(R.string.delete),
                    getString(R.string.cancel)
                )
            )
            is CalculationViewModel.OpenSaveCalculationViewModelEvent -> {

            }
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                viewModel.onOpenSaveCalculation()
                true
            }
            R.id.delete -> {
                viewModel.onOpenDeleteCalculationConfirmation()
                true
            }
            else -> false
        }
    }

    private fun setupView() = binding.apply {
        toolbar.setOnMenuItemClickListener(this@CalculationFragment)
        pager.adapter = calculationPagerAdapter
        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.text = when (position) {
                CalculationPagerAdapter.TAB_GENERAL_POSITION -> getString(R.string.credit_calculation_tab_general)
                CalculationPagerAdapter.TAB_PAYMENTS_POSITION -> getString(R.string.credit_calculation_tab_payments)
                else -> throw IllegalArgumentException("Incorrect position")
            }
        }.attach()
    }

    private fun setupViewModel() {
        viewModel.creditCalculationId.observe { creditCalculationId ->
            val saveItem = binding.toolbar.menu.findItem(R.id.save)
            val deleteItem = binding.toolbar.menu.findItem(R.id.delete)
            saveItem.isVisible = creditCalculationId <= 0
            deleteItem.isVisible = true
        }
    }

}
