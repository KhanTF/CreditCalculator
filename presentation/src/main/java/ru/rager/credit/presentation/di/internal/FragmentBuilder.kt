package ru.rager.credit.presentation.di.internal

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.di.internal.submodule.CalculationResultFragmentSubmodule
import ru.rager.credit.presentation.ui.calculation.CalculationFragment
import ru.rager.credit.presentation.ui.calculation.general.CalculationGeneralFragment
import ru.rager.credit.presentation.ui.calculation.payments.CalculationPaymentsFragment
import ru.rager.credit.presentation.ui.earlypayment.EarlyPaymentFragment
import ru.rager.credit.presentation.ui.main.MainFragment
import ru.rager.credit.presentation.ui.paymentcalculator.PaymentCalculatorFragment
import ru.rager.credit.presentation.ui.percentcalculator.PercentCalculatorFragment

@Module
internal abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun buildMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun buildPaymentCalculatorFragment(): PaymentCalculatorFragment

    @ContributesAndroidInjector
    abstract fun buildEarlyPaymentFragment(): EarlyPaymentFragment

    @ContributesAndroidInjector
    abstract fun buildPercentCalculatorFragment(): PercentCalculatorFragment

    @ContributesAndroidInjector(modules = [CalculationResultFragmentSubmodule::class])
    abstract fun buildCalculationFragment(): CalculationFragment

    @ContributesAndroidInjector
    abstract fun buildCalculationResultGeneralFragment(): CalculationGeneralFragment

    @ContributesAndroidInjector
    abstract fun buildCalculationResultPaymentScheduleFragment(): CalculationPaymentsFragment
/*
    @ContributesAndroidInjector
    abstract fun buildCalculationListFragment(): CalculationListFragment*/

}