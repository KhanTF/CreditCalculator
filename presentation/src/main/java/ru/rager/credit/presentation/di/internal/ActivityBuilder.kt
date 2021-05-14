package ru.rager.credit.presentation.di.internal

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.ui.RootActivity

@Module
internal abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [FragmentBuilder::class])
    abstract fun buildMainActivity(): RootActivity

}