package ru.rager.credit.presentation.di.internal.ui.fragments

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.ui.pretermpayment.PretermPaymentFragment
import ru.rager.credit.presentation.ui.pretermpayment.PretermPaymentFragmentArgs

@Module
abstract class PretermPaymentFragmentBuilder {

    @ContributesAndroidInjector(modules = [PretermPaymentFragmentModule::class])
    abstract fun buildPretermPaymentFragment(): PretermPaymentFragment

    @Module
    class PretermPaymentFragmentModule {

        @Provides
        fun providePretermPaymentFragmentArguments(fragment: PretermPaymentFragment): PretermPaymentFragmentArgs {
            return PretermPaymentFragmentArgs.fromBundle(fragment.requireArguments())
        }

    }

}