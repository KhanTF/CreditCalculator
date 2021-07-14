package ru.rager.credit.presentation.di.internal.ui.fragments

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.rager.credit.presentation.ui.main.MainFragment

@Module
abstract class MainFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun buildMainFragment(): MainFragment

}