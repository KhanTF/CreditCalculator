package ru.rager.credit.presentation.screen

import com.github.terrakok.cicerone.Screen
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.SavedCreditCalculationParameterEntity

interface ScreenFactory {

    fun getMainScreen(): Screen

    fun getPaymentCalculatorScreen(): Screen

    fun getPercentCalculatorScreen(): Screen

    fun getCalculationScreen(creditCalculationParameterEntity: CreditCalculationParameterEntity): Screen

    fun getCalculationListScreen(): Screen

}