package ru.rager.credit.presentation.screen

import com.github.terrakok.cicerone.Screen
import ru.rager.credit.presentation.model.CalculationParameters

interface ScreenFactory {

    fun getMainScreen(): Screen

    fun getPaymentCalculatorScreen(): Screen

    fun getCalculationResultScreen(calculationParameters: CalculationParameters): Screen

}