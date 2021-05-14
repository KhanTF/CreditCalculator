package ru.rager.credit.presentation.di.internal

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.ui.paymentcalculator.PaymentCalculatorFragment
import ru.rager.credit.presentation.ui.main.MainFragment

@Module
internal abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun buildMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun buildCalculatorFragment(): PaymentCalculatorFragment

}