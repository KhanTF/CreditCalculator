package ru.rager.credit.presentation.ui.calculation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.databinding.FragmentCalculationBinding
import ru.rager.credit.presentation.ui.base.BaseFragment
import ru.rager.credit.presentation.util.setupWithViewPager2

class CalculationFragment : BaseFragment<CalculationViewModel, FragmentCalculationBinding>(), Toolbar.OnMenuItemClickListener {

    private val calculationPagerAdapter by lazy { CalculationPagerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.toolbar.setOnMenuItemClickListener(this)
        binding.pager.adapter = calculationPagerAdapter
        binding.tabs.setupWithViewPager2(binding.pager) { position ->
            when (position) {
                CalculationPagerAdapter.TAB_GENERAL_POSITION -> getString(R.string.credit_calculation_tab_general)
                CalculationPagerAdapter.TAB_PAYMENTS_POSITION -> getString(R.string.credit_calculation_tab_payments)
                else -> throw IllegalArgumentException("Incorrect position")
            }
        }
        viewModel.calculationId.observe { creditCalculationId ->
            val saveItem = binding.toolbar.menu.findItem(R.id.save)
            val deleteItem = binding.toolbar.menu.findItem(R.id.delete)
            saveItem.isVisible = creditCalculationId <= 0
            deleteItem.isVisible = true
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

}
