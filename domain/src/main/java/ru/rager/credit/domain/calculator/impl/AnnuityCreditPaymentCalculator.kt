package ru.rager.credit.domain.calculator.impl

import ru.rager.credit.domain.calculator.CreditPaymentCalculator
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.max
import kotlin.math.pow

class AnnuityCreditPaymentCalculator @Inject constructor() : CreditPaymentCalculator() {

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
        val p = getPForPeriod(creditRate, creditRatePeriod, creditPaymentPeriod)
        return max(creditSum * (p + p / ((1 + p).pow(creditTerm) - 1)), creditMinPaymentSum)
    }

    override fun getTerm(
        creditSum: Double,
        creditPaymentSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRatePeriod: CreditPeriodType,
        creditPaymentPeriod: CreditPeriodType
    ): Int {
        val p = getPForPeriod(creditRate, creditRatePeriod, creditPaymentPeriod)
        return ceil(log(creditPaymentSum / (creditPaymentSum - p * creditPaymentSum), 1 + p)).toInt()
    }

}