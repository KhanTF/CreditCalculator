package ru.rager.credit.presentation.di.internal.ui.fragments

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.ui.createearlypayment.CreateEarlyPaymentFragment

@Module
abstract class CreateEarlyPaymentFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun buildCreateEarlyPaymentFragment(): CreateEarlyPaymentFragment

}