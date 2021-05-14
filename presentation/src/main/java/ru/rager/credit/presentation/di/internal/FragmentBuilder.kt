package ru.rager.credit.presentation.di.internal

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.di.internal.submodule.CalculationResultFragmentSubmodule
import ru.rager.credit.presentation.ui.calculationresult.CalculationResultFragment
import ru.rager.credit.presentation.ui.calculationresult.general.CalculationResultGeneralFragment
import ru.rager.credit.presentation.ui.calculationresult.paymentschedule.CalculationResultPaymentScheduleFragment
import ru.rager.credit.presentation.ui.paymentcalculator.PaymentCalculatorFragment
import ru.rager.credit.presentation.ui.main.MainFragment

@Module
internal abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun buildMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun buildCalculatorFragment(): PaymentCalculatorFragment

    @ContributesAndroidInjector(modules = [CalculationResultFragmentSubmodule::class])
    abstract fun buildCalculationResultFragment(): CalculationResultFragment

    @ContributesAndroidInjector
    abstract fun buildCalculationResultGeneralFragment(): CalculationResultGeneralFragment

    @ContributesAndroidInjector
    abstract fun buildCalculationResultPaymentScheduleFragment(): CalculationResultPaymentScheduleFragment

}