package ru.rager.credit.presentation.screen

import com.github.terrakok.cicerone.Screen
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.presentation.ui.calculation.CalculationViewModel
import ru.rager.credit.presentation.util.getDoubleValue
import ru.rager.credit.presentation.util.getIntValue

interface ScreenFactory {

    fun getMainScreen(): Screen

    fun getPaymentCalculatorScreen(): Screen

    fun getPercentCalculatorScreen(): Screen

    fun getCalculationScreen(parameters: CalculationViewModel.Parameters): Screen

    fun getCalculationListScreen(): Screen

}