package ru.rager.credit.presentation.di

import dagger.Module
import ru.rager.credit.presentation.di.internal.ActivityBuilder
import ru.rager.credit.presentation.di.internal.FragmentBuilder
import ru.rager.credit.presentation.di.internal.NavigationModule
import ru.rager.credit.presentation.di.internal.ViewModelModule

@Module(
    includes = [
        ActivityBuilder::class,
        ViewModelModule::class,
        NavigationModule::class
    ]
)
class PresentationModule