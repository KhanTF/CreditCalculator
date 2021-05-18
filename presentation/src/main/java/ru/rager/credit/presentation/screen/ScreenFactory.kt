package ru.rager.credit.presentation.screen

import com.github.terrakok.cicerone.Screen
import ru.rager.credit.domain.entity.CreditCalculationEntity

interface ScreenFactory {

    fun getMainScreen(): Screen

    fun getPaymentCalculatorScreen(): Screen

    fun getPercentCalculatorScreen(): Screen

    fun getCalculationScreen(creditCalculationEntity: CreditCalculationEntity): Screen

}