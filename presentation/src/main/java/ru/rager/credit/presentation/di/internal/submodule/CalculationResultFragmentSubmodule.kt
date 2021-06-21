package ru.rager.credit.presentation.di.internal.submodule

import dagger.Module
import dagger.Provides
import ru.rager.credit.presentation.ui.calculation.CalculationFragment
import ru.rager.credit.presentation.ui.calculation.CalculationViewModel

@Module
class CalculationResultFragmentSubmodule {

    @Provides
    fun provideParameters(calculationFragment: CalculationFragment): CalculationViewModel.Parameters {
        return calculationFragment.parameters
    }

}