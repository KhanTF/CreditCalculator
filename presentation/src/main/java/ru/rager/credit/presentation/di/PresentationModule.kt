package ru.rager.credit.presentation.di

import dagger.Module
import ru.rager.credit.presentation.di.internal.ActivityBuilder
import ru.rager.credit.presentation.di.internal.NavigationModule

@Module(
    includes = [
        ActivityBuilder::class,
        NavigationModule::class
    ]
)
class PresentationModule