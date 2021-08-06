package ru.rager.credit.presentation.di.internal.ui.fragments

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.domain.entity.enums.CalculatorType
import ru.rager.credit.presentation.ui.creditcalculator.*
import javax.inject.Provider

@Module
abstract class CreditCalculatorFragmentBuilder {

    @ContributesAndroidInjector(modules = [CreditCalculatorFragmentModule::class])
    abstract fun buildCreditCalculatorFragment(): CreditCalculatorFragment

    @Module
    class CreditCalculatorFragmentModule {

        @Provides
        fun provideCreditCalculatorFragmentArgs(creditCalculatorFragment: CreditCalculatorFragment): CreditCalculatorFragmentArgs {
            return CreditCalculatorFragmentArgs.fromBundle(creditCalculatorFragment.requireArguments())
        }

        @Provides
        fun provideCreditCalculatorViewModel(
            arguments: CreditCalculatorFragmentArgs,
            standard: Provider<StandardCreditCalculatorViewModel>,
            percent: Provider<PercentCreditCalculatorViewModel>
        ): CreditCalculatorViewModel {
            return when (arguments.type) {
                CalculatorType.STANDARD_CALCULATOR -> standard.get()
                CalculatorType.PERCENT_CALCULATOR -> percent.get()
            }
        }

    }

}