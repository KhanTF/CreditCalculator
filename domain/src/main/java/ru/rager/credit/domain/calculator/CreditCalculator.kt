package ru.rager.credit.domain.calculator

import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.exceptions.CalculationException
import kotlin.math.abs

interface CreditCalculator {

    companion object {
        const val PERCENT_CALCULATION_FAULT = 1
        const val PERCENT_CALCULATION_START_RATE = 0.0
        const val PERCENT_CALCULATION_START_ACCURACY = 1.0
    }

    fun getPayment(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        paymentIndex: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Double

    fun getPercentPartOfPayment(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        paymentIndex: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Double

    fun getDebtPartOfPayment(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        paymentIndex: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Double

    fun getDebtRemainingBeforePayment(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        paymentIndex: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Double

    fun getDebtRemainingAfterPayment(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        paymentIndex: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Double

    fun getRate(
        sum: Double,
        payment: Double,
        term: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Double

    fun getRecursiveRate(
        sum: Double,
        payment: Double,
        term: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType,
        startRate: Double,
        accuracy: Double
    ): Double {
        if (startRate < 0) throw CalculationException("Credit payment must be more than 0")
        if (startRate > 100) throw CalculationException("Credit rate must be less than 100")
        val actual1 = getPayment(sum, startRate, term, 0, creditRateFrequency, creditPaymentFrequency)
        return when {
            actual1 == payment || abs(actual1 - payment) < PERCENT_CALCULATION_FAULT -> startRate
            actual1 > payment -> getRecursiveRate(sum, payment, term, creditRateFrequency, creditPaymentFrequency, startRate - accuracy, accuracy / 10)
            else -> getRecursiveRate(sum, payment, term, creditRateFrequency, creditPaymentFrequency, startRate + accuracy, accuracy)
        }
    }

    fun getP(
        creditRate: Double,
        creditRateFrequency: CreditFrequencyType,
        paymentCreditFrequency: CreditFrequencyType
    ) = creditRate / 100 / (creditRateFrequency.value / paymentCreditFrequency.value)

}