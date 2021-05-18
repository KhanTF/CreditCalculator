package ru.rager.credit.domain.utils

object CreditConstants {

    const val MIN_CREDIT_SUM_EXCLUSIVE = 0.0
    const val MAX_CREDIT_SUM_EXCLUSIVE = Double.MAX_VALUE

    const val MIN_CREDIT_RATE_EXCLUSIVE = 0.0
    const val MAX_CREDIT_RATE_EXCLUSIVE = 100.0

    const val MIN_CREDIT_TERM_EXCLUSIVE = 0
    const val MAX_CREDIT_TERM_EXCLUSIVE = 600

    const val MIN_CREDIT_PAYMENT_EXCLUSIVE = 0
    const val MAX_CREDIT_PAYMENT_EXCLUSIVE = Double.MAX_VALUE

    const val EVERY_MONTH_FREQUENCY = 12

    internal const val PERCENT_CALCULATION_FAULT = 0.5
    internal const val PERCENT_CALCULATION_START_RATE = 0.0
    internal const val PERCENT_CALCULATION_START_ACCURACY = 1.0
    internal const val PERCENT_CALCULATION_MAX_ACCURACY = 0.00001

    fun isCreditSumValid(sum: Double?): Boolean {
        return sum != null && sum > MIN_CREDIT_SUM_EXCLUSIVE && sum < MAX_CREDIT_SUM_EXCLUSIVE
    }

    fun isCreditRateValid(rate: Double?): Boolean {
        return rate != null && rate > MIN_CREDIT_RATE_EXCLUSIVE && rate < MAX_CREDIT_RATE_EXCLUSIVE
    }

    fun isCreditTermValid(term: Int?): Boolean {
        return term != null && term > MIN_CREDIT_TERM_EXCLUSIVE && term < MAX_CREDIT_TERM_EXCLUSIVE
    }

    fun isCreditPaymentValid(payment: Double?): Boolean {
        return payment != null && payment > MIN_CREDIT_PAYMENT_EXCLUSIVE && payment < MAX_CREDIT_PAYMENT_EXCLUSIVE
    }

    internal fun getP(creditRate: Double, frequency: Int = EVERY_MONTH_FREQUENCY) = creditRate / 100 / frequency

}