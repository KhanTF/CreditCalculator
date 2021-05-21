package ru.rager.credit.presentation.di.internal.submodule

import dagger.Module
import dagger.Provides
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.presentation.ui.calculation.CalculationFragment
import ru.rager.credit.presentation.ui.calculation.CalculationViewModel
import javax.inject.Named

@Module
class CalculationResultFragmentSubmodule {

    @Provides
    fun provideParameters(calculationFragment: CalculationFragment): CalculationViewModel.Parameters {
        return calculationFragment.parameters
    }

}