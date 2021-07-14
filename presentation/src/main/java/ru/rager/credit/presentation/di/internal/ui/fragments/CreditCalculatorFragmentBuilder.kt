package ru.rager.credit.presentation.di.internal.ui.fragments

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.ui.creditcalculator.CreditCalculatorFragment

@Module
abstract class CreditCalculatorFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun buildCreditCalculatorFragment(): CreditCalculatorFragment

}