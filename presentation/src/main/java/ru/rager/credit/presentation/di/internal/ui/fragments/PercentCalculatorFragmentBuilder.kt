package ru.rager.credit.presentation.di.internal.ui.fragments

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.ui.percentcalculator.PercentCalculatorFragment

@Module
abstract class PercentCalculatorFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun buildPercentCalculatorFragment(): PercentCalculatorFragment

}
