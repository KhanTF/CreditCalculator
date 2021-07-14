package ru.rager.credit.presentation.di.internal.ui.fragments

import dagger.Module

@Module(
    includes = [
        CalculationFragmentBuilder::class,
        PercentCalculatorFragmentBuilder::class,
        CreditCalculatorFragmentBuilder::class,
        MainFragmentBuilder::class
    ]
)
class FragmentBuilders