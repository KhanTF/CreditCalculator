package ru.rager.credit.domain.calculator

import ru.rager.credit.domain.entity.enums.PeriodType
import kotlin.math.abs

abstract class CreditRateCalculator {

    companion object {
        const val PERCENT_CALCULATION_FAULT = 1
        const val PERCENT_CALCULATION_START_RATE = 0.0
        const val PERCENT_CALCULATION_START_ACCURACY = 1.0
    }

    fun calculate(
        creditSum: Double,
        creditPaymentSum: Double,
        creditTerm: Int,
        creditRatePeriod: PeriodType,
        creditPaymentPeriod: PeriodType
    ) = getRate(
        creditSum = creditSum,
        creditPaymentSum = creditPaymentSum,
        creditRate = PERCENT_CALCULATION_START_RATE,
        creditTerm = creditTerm,
        creditRatePeriod = creditRatePeriod,
        creditPaymentPeriod = creditPaymentPeriod,
        creditRateAccuracy = PERCENT_CALCULATION_START_ACCURACY
    )

    protected fun getPForPeriod(
        creditRate: Double,
        creditRatePeriod: PeriodType,
        creditPaymentPeriod: PeriodType
    ): Double {
        return creditRate / 100 / (creditRatePeriod.value / creditPaymentPeriod.value)
    }

    protected abstract fun getPayment(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRatePeriod: PeriodType,
        creditPaymentPeriod: PeriodType
    ): Double

    private tailrec fun getRate(
        creditSum: Double,
        creditPaymentSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRatePeriod: PeriodType,
        creditPaymentPeriod: PeriodType,
        creditRateAccuracy: Double
    ): Double {
        if (creditRate < 0 || creditRate > 100) {
            return -1.0
        }
        val actualCreditPaymentSum = getPayment(creditSum, creditRate, creditTerm, creditRatePeriod, creditPaymentPeriod)
        if (abs(actualCreditPaymentSum - creditPaymentSum) < PERCENT_CALCULATION_FAULT) {
            return creditRate
        }
        val newCreditRateAccuracy = when {
            actualCreditPaymentSum > creditPaymentSum -> creditRateAccuracy / 100
            else -> creditRateAccuracy
        }
        val newCreditRate = when {
            actualCreditPaymentSum > creditPaymentSum -> creditRate - creditRateAccuracy
            else -> creditRate + creditRateAccuracy
        }
        return getRate(
            creditSum = creditSum,
            creditPaymentSum = creditPaymentSum,
            creditRate = newCreditRate,
            creditTerm = creditTerm,
            creditRatePeriod = creditRatePeriod,
            creditPaymentPeriod = creditPaymentPeriod,
            creditRateAccuracy = newCreditRateAccuracy
        )
    }

}