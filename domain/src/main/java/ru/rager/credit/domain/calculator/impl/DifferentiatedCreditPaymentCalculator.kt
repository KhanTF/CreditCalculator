package ru.rager.credit.domain.calculator.impl

import ru.rager.credit.domain.calculator.CreditPaymentCalculator
import ru.rager.credit.domain.entity.enums.PeriodType
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.max

class DifferentiatedCreditPaymentCalculator @Inject constructor() : CreditPaymentCalculator() {

    override fun getPayment(
        creditSum: Double,
        creditMinPaymentSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRatePeriod: PeriodType,
        creditPaymentPeriod: PeriodType,
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
        creditRatePeriod: PeriodType,
        creditPaymentPeriod: PeriodType
    ): Int {
        return ceil(creditSum / creditPaymentSum).toInt()
    }

}