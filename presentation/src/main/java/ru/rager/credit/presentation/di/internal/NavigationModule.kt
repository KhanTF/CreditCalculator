package ru.rager.credit.presentation.di.internal

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.screen.ScreenFactoryImpl
import javax.inject.Singleton

@Module
internal class NavigationModule {

    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router

    @Provides
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder =
        cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideScreenFactory(): ScreenFactory = ScreenFactoryImpl()

}