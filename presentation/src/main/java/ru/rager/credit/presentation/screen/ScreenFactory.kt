package ru.rager.credit.presentation.screen

import com.github.terrakok.cicerone.Screen
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.presentation.util.getDoubleValue
import ru.rager.credit.presentation.util.getIntValue

interface ScreenFactory {

    fun getMainScreen(): Screen

    fun getPaymentCalculatorScreen(): Screen

    fun getPercentCalculatorScreen(): Screen

    fun getCalculationScreen(
        id: Long? = null,
        name: String? = null,
        creditRateType: CreditRateType,
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int
    ): Screen

    fun getCalculationListScreen(): Screen

}