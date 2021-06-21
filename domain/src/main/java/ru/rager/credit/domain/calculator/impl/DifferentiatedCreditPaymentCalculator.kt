package ru.rager.credit.domain.calculator.impl

import ru.rager.credit.domain.calculator.CreditPaymentCalculator
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.max
import kotlin.math.pow

class DifferentiatedCreditPaymentCalculator @Inject constructor() : CreditPaymentCalculator() {

    override fun getPayment(
        creditSum: Double,
        creditMinPaymentSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRatePeriod: CreditPeriodType,
        creditPaymentPeriod: CreditPeriodType,
        currentTotalRemainingDebtSum: Double,
        currentTotalAccruedPercentSum: Double
    ): Double {
        return max(creditSum / creditTerm, creditMinPaymentSum) + currentTotalAccruedPercentSum
    }

    override fun getTerm(
        creditSum: Double,
        creditPaymentSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRatePeriod: CreditPeriodType,
        creditPaymentPeriod: CreditPeriodType
    ): Int {
        return ceil(creditSum / creditPaymentSum).toInt()
    }

}