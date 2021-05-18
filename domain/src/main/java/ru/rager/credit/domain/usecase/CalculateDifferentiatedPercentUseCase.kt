package ru.rager.credit.domain.usecase

import io.reactivex.Single
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.exceptions.PaymentTooMuchException
import ru.rager.credit.domain.exceptions.PaymentTooSmallException
import ru.rager.credit.domain.utils.CreditConstants
import ru.rager.credit.domain.utils.CreditConstants.PERCENT_CALCULATION_FAULT
import ru.rager.credit.domain.utils.CreditConstants.PERCENT_CALCULATION_MAX_ACCURACY
import ru.rager.credit.domain.utils.CreditConstants.PERCENT_CALCULATION_START_ACCURACY
import ru.rager.credit.domain.utils.CreditConstants.PERCENT_CALCULATION_START_RATE
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.pow

class CalculateDifferentiatedPercentUseCase @Inject constructor(private val calculateDifferentiatedPaymentUseCase: CalculateDifferentiatedPaymentUseCase) {

    fun getDifferentiatedCalculation(
        creditDebtSum: Double,
        creditMonthPayment: Double,
        creditTerm: Int
    ): Single<CreditCalculationEntity.TempCreditCalculationEntity> = Single.defer {
        val rate = getDifferentiatedRate(creditDebtSum, creditMonthPayment, creditTerm)
        when {
            rate < CreditConstants.MIN_CREDIT_RATE_EXCLUSIVE -> {
                throw PaymentTooSmallException()
            }
            rate >= CreditConstants.MAX_CREDIT_RATE_EXCLUSIVE -> {
                throw PaymentTooMuchException()
            }
            else -> {
                calculateDifferentiatedPaymentUseCase.getDifferentiatedCalculation(creditDebtSum, rate, creditTerm)
            }
        }
    }

    private fun getDifferentiatedRate(
        sum: Double,
        payment: Double,
        term: Int,
        startRate: Double = PERCENT_CALCULATION_START_RATE,
        accuracy: Double = PERCENT_CALCULATION_START_ACCURACY,
        maxAccuracy: Double = PERCENT_CALCULATION_MAX_ACCURACY
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
            actual1 == payment || abs(actual1 - payment) < PERCENT_CALCULATION_FAULT -> startRate
            actual1 > payment -> if (accuracy > maxAccuracy) {
                getDifferentiatedRate(sum, payment, term, startRate - accuracy, accuracy / 10, maxAccuracy)
            } else {
                return startRate - accuracy
            }
            else -> getDifferentiatedRate(sum, payment, term, startRate + accuracy, accuracy, maxAccuracy)
        }
    }

}