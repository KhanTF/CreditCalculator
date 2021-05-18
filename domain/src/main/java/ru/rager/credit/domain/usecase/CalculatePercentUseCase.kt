package ru.rager.credit.domain.usecase

import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.exceptions.PaymentTooMuchException
import ru.rager.credit.domain.exceptions.PaymentTooSmallException
import ru.rager.credit.domain.utils.CreditConstants
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.pow

class CalculatePercentUseCase @Inject constructor(private val calculatePaymentUseCase: CalculatePaymentUseCase) {

    companion object {
        private const val FAULT = 0.5
        private const val START_RATE = 0.0
        private const val START_ACCURACY = 1.0
        private const val MAX_ACCURACY = 0.00001
    }

    fun getAnnuityCalculation(
        creditDebtSum: Double,
        creditMonthPayment: Double,
        creditTerm: Int
    ): CreditCalculationEntity {
        val rate = getAnnuityRate(creditDebtSum, creditMonthPayment, creditTerm)
        return when {
            rate < CreditConstants.MIN_CREDIT_RATE_EXCLUSIVE -> {
                throw PaymentTooSmallException()
            }
            rate >= CreditConstants.MAX_CREDIT_RATE_EXCLUSIVE -> {
                throw PaymentTooMuchException()
            }
            else -> {
                calculatePaymentUseCase.getAnnuityCalculation(creditDebtSum, rate, creditTerm)
            }
        }
    }

    fun getDifferentiatedCalculation(
        creditDebtSum: Double,
        creditMonthPayment: Double,
        creditTerm: Int
    ): CreditCalculationEntity {
        val rate = getDifferentiatedRate(creditDebtSum, creditMonthPayment, creditTerm)
        return when {
            rate < CreditConstants.MIN_CREDIT_RATE_EXCLUSIVE -> {
                throw PaymentTooSmallException()
            }
            rate >= CreditConstants.MAX_CREDIT_RATE_EXCLUSIVE -> {
                throw PaymentTooMuchException()
            }
            else -> {
                calculatePaymentUseCase.getDifferentiatedCalculation(creditDebtSum, rate, creditTerm)
            }
        }
    }

    private fun getAnnuityRate(
        sum: Double,
        payment: Double,
        term: Int,
        startRate: Double = START_RATE,
        accuracy: Double = START_ACCURACY,
        maxAccuracy: Double = MAX_ACCURACY
    ): Double {
        if (startRate < 0) {
            return -1.0
        }
        val actual1 = if (startRate > 0) {
            val p1 = startRate / 100 / 12
            sum * (p1 + p1 / ((1 + p1).pow(term) - 1))
        } else {
            sum / term
        }
        return when {
            actual1 == payment || abs(actual1 - payment) < FAULT -> startRate
            actual1 > payment -> if (accuracy > maxAccuracy) {
                getAnnuityRate(sum, payment, term, startRate - accuracy, accuracy / 10, maxAccuracy)
            } else {
                return startRate - accuracy
            }
            else -> getAnnuityRate(sum, payment, term, startRate + accuracy, accuracy, maxAccuracy)
        }
    }

    private fun getDifferentiatedRate(
        sum: Double,
        payment: Double,
        term: Int,
        startRate: Double = START_RATE,
        accuracy: Double = START_ACCURACY,
        maxAccuracy: Double = MAX_ACCURACY
    ): Double {
        if (startRate < 0) {
            return -1.0
        }
        val actual1 = if (startRate > 0) {
            val p1 = startRate / 100 / 12
            sum / term + sum * p1
        } else {
            sum / term
        }
        return when {
            actual1 == payment || abs(actual1 - payment) < FAULT -> startRate
            actual1 > payment -> if (accuracy > maxAccuracy) {
                getDifferentiatedRate(sum, payment, term, startRate - accuracy, accuracy / 10, maxAccuracy)
            } else {
                return startRate - accuracy
            }
            else -> getDifferentiatedRate(sum, payment, term, startRate + accuracy, accuracy, maxAccuracy)
        }
    }

}