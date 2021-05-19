package ru.rager.credit.presentation.di.internal.submodule

import dagger.Module
import dagger.Provides
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.presentation.ui.calculation.CalculationFragment

@Module
class CalculationResultFragmentSubmodule {

    @Provides
    fun provideCalculationParameterEntity(calculationFragment: CalculationFragment): CreditCalculationParameterEntity {
        return calculationFragment.getCreditCalculationParameterArgument()
    }

}