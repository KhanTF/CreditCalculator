package ru.rager.credit.presentation.ui.percentcalculator

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.setupWithNavController
import androidx.savedstate.SavedStateRegistry
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.databinding.FragmentPercentCalculatorBinding
import ru.rager.credit.presentation.dto.CreditParametersParcelable
import ru.rager.credit.presentation.ui.base.BaseFragment

class PercentCalculatorFragment : BaseFragment<PercentCalculatorViewModel, FragmentPercentCalculatorBinding>() {

    companion object {
        fun getInstance() = PercentCalculatorFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.openCalculation.observe {
            navController.navigate(PercentCalculatorFragmentDirections.toCalculationFragment(CreditParametersParcelable(it)))
        }
    }

}