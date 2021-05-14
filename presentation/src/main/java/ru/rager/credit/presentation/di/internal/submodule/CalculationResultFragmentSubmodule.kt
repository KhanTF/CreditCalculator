package ru.rager.credit.presentation.di.internal.submodule

import dagger.Module
import dagger.Provides
import ru.rager.credit.presentation.model.CalculationParameters
import ru.rager.credit.presentation.ui.calculationresult.CalculationResultFragment

@Module
class CalculationResultFragmentSubmodule {

    @Provides
    fun provideCalculationParameters(calculationResultFragment: CalculationResultFragment) : CalculationParameters{
        return calculationResultFragment.calculationParameters
    }

}