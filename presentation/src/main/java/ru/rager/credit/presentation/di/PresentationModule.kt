package ru.rager.credit.presentation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.rager.credit.presentation.di.internal.ui.activity.RootActivityBuilder

@Module(includes = [RootActivityBuilder::class])
class PresentationModule {

    @Provides
    fun provideResources(context: Context) = context.resources

}