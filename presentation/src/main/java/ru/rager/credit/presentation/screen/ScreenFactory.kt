package ru.rager.credit.presentation.screen

import com.github.terrakok.cicerone.Screen
import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.presentation.ui.calculation.CalculationViewModel

interface ScreenFactory {

    fun getMainScreen(): Screen

    fun getPaymentCalculatorScreen(): Screen

    fun getPercentCalculatorScreen(): Screen

    fun getCalculationScreen(parameters: CalculationViewModel.Parameters): Screen

    fun getCalculationListScreen(): Screen

}