package ru.rager.credit.presentation.di.internal.ui.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.di.internal.ui.fragments.*
import ru.rager.credit.presentation.ui.RootActivity

@Module
abstract class RootActivityBuilder {

    @ContributesAndroidInjector(
        modules = [
            CalculationFragmentBuilder::class,
            CreditCalculatorFragmentBuilder::class,
            MainFragmentBuilder::class,
            PretermPaymentFragmentBuilder::class,
            SimpleDialogFragmentBuilder::class,
            DatePickerDialogFragmentBuilder::class
        ]
    )
    abstract fun buildRootActivity(): RootActivity

}