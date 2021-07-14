package ru.rager.credit.presentation.di.internal.ui.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.di.internal.ui.dialogs.DialogBuilders
import ru.rager.credit.presentation.di.internal.ui.dialogs.SimpleDialogFragmentBuilder
import ru.rager.credit.presentation.di.internal.ui.fragments.*
import ru.rager.credit.presentation.ui.RootActivity

@Module
abstract class RootActivityBuilder {

    @ContributesAndroidInjector(
        modules = [
            FragmentBuilders::class,
            DialogBuilders::class
        ]
    )
    abstract fun buildRootActivity(): RootActivity

}