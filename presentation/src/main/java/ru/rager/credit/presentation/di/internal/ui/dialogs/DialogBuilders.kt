package ru.rager.credit.presentation.di.internal.ui.dialogs

import dagger.Module

@Module(
    includes = [
        DatePickerDialogFragmentBuilder::class,
        SimpleDialogFragmentBuilder::class
    ]
)
class DialogBuilders