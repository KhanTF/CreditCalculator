package ru.rager.credit.presentation.di.internal.submodule

import dagger.Module
import dagger.Provides
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.presentation.ui.calculation.CalculationFragment

@Module
class CalculationResultFragmentSubmodule {

    @Provides
    fun provideCalculationParameters(calculationFragment: CalculationFragment): CreditCalculationEntity {
        return calculationFragment.getCreditCalculationArgument()
    }

}