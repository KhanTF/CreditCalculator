package ru.rager.credit.presentation.di.internal.ui.fragments

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.ui.dialog.datepicker.DatePickerDialogFragment
import ru.rager.credit.presentation.ui.dialog.datepicker.DatePickerDialogFragmentArgs

@Module
abstract class DatePickerDialogFragmentBuilder {

    @ContributesAndroidInjector(modules = [DatePickerDialogFragmentModule::class])
    abstract fun buildDatePickerDialogFragment(): DatePickerDialogFragment

    @Module
    class DatePickerDialogFragmentModule {

        @Provides
        fun provideArguments(datePickerDialogFragment: DatePickerDialogFragment): DatePickerDialogFragmentArgs {
            return DatePickerDialogFragmentArgs.fromBundle(datePickerDialogFragment.requireArguments())
        }

    }
}