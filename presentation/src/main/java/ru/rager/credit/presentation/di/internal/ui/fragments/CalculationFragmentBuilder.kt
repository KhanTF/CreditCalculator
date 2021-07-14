package ru.rager.credit.presentation.di.internal.ui.fragments

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.ui.calculation.CalculationPagerAdapter
import ru.rager.credit.presentation.ui.calculation.CalculationFragment
import ru.rager.credit.presentation.ui.calculation.CalculationFragmentArgs
import ru.rager.credit.presentation.ui.calculation.CalculationGeneralFragment
import ru.rager.credit.presentation.ui.calculation.CalculationPaymentsFragment

@Module
abstract class CalculationFragmentBuilder {

    @ContributesAndroidInjector(modules = [CalculationFragmentModule::class, CalculationGeneralFragmentBuilder::class, CalculationPaymentsFragmentBuilder::class])
    abstract fun buildCalculationFragment(): CalculationFragment

    @Module
    class CalculationFragmentModule {
        @Provides
        fun provideParameters(calculationFragment: CalculationFragment): CalculationFragmentArgs {
            return CalculationFragmentArgs.fromBundle(calculationFragment.requireArguments())
        }

        @Provides
        fun provideCalculationPagerAdapter(calculationFragment: CalculationFragment): CalculationPagerAdapter {
            return CalculationPagerAdapter(calculationFragment)
        }
    }

    @Module
    abstract class CalculationGeneralFragmentBuilder {
        @ContributesAndroidInjector
        abstract fun buildCalculationGeneralFragment(): CalculationGeneralFragment
    }

    @Module
    abstract class CalculationPaymentsFragmentBuilder {
        @ContributesAndroidInjector
        abstract fun buildCalculationPaymentsFragment(): CalculationPaymentsFragment
    }


}