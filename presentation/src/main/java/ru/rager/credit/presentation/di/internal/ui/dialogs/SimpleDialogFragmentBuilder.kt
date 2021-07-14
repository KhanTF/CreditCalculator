package ru.rager.credit.presentation.di.internal.ui.dialogs

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.ui.calculation.CalculationFragment
import ru.rager.credit.presentation.ui.dialog.simple.SimpleDialogFragment
import ru.rager.credit.presentation.ui.dialog.simple.SimpleDialogFragmentArgs

@Module
abstract class SimpleDialogFragmentBuilder {

    @ContributesAndroidInjector(modules = [SimpleDialogFragmentModule::class])
    abstract fun buildSimpleDialogFragment(): SimpleDialogFragment

    @Module
    class SimpleDialogFragmentModule {

        @Provides
        fun provideArguments(simpleDialogFragment: SimpleDialogFragment): SimpleDialogFragmentArgs {
            return SimpleDialogFragmentArgs.fromBundle(simpleDialogFragment.requireArguments())
        }

    }
}