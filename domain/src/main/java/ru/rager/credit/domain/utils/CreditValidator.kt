package ru.rager.credit.domain.utils

import kotlin.math.pow

object CreditValidator {

    const val MIN_CREDIT_SUM_EXCLUSIVE = 0.0
    const val MAX_CREDIT_SUM_EXCLUSIVE = Double.MAX_VALUE

    const val MIN_CREDIT_RATE_EXCLUSIVE = 0.0
    const val MAX_CREDIT_RATE_EXCLUSIVE = 100.0

    const val MIN_CREDIT_TERM_EXCLUSIVE = 0
    const val MAX_CREDIT_TERM_EXCLUSIVE = 600

    const val MIN_CREDIT_PAYMENT_EXCLUSIVE = 0
    const val MAX_CREDIT_PAYMENT_EXCLUSIVE = Double.MAX_VALUE

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

}