package ru.rager.credit.presentation.di.internal.submodule

import dagger.Module
import dagger.Provides
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.presentation.ui.calculation.CalculationFragment
import javax.inject.Named

@Module
class CalculationResultFragmentSubmodule {

    @Provides
    @Named("creditCalculationId")
    fun provideCalculationId(calculationFragment: CalculationFragment): Long? {
        return calculationFragment.parameters.id
    }

    @Provides
    @Named("creditCalculationName")
    fun provideCalculationName(calculationFragment: CalculationFragment): String? {
        return calculationFragment.parameters.name
    }

    @Provides
    @Named("creditRateType")
    fun provideCreditRateType(calculationFragment: CalculationFragment): CreditRateType {
        return calculationFragment.parameters.creditRateType
    }

    @Provides
    @Named("creditSum")
    fun provideCreditSum(calculationFragment: CalculationFragment): Double {
        return calculationFragment.parameters.creditSum
    }

    @Provides
    @Named("creditRate")
    fun provideCreditRate(calculationFragment: CalculationFragment): Double {
        return calculationFragment.parameters.creditRate
    }

    @Provides
    @Named("creditTerm")
    fun provideCreditTerm(calculationFragment: CalculationFragment): Int {
        return calculationFragment.parameters.creditTerm
    }


}