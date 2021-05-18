package ru.rager.credit.domain.usecase

import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import ru.rager.credit.domain.entity.CreditCalculationPaymentEntity
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

class CalculatePaymentUseCase @Inject constructor() {

    companion object {
        private const val TOTAL_PERCENT = 100
        private const val MONTH_IN_YEAR = 12
    }

    fun getAnnuityCalculation(
        creditDebtSum: Double,
        creditRate: Double,
        creditTerm: Int
    ): CreditCalculationEntity {
        val p = creditRate / TOTAL_PERCENT / MONTH_IN_YEAR
        val payment = when {
            creditRate <= 0 -> creditDebtSum / creditTerm
            else -> creditDebtSum * (p + p / ((1 + p).pow(creditTerm) - 1))
        }
        var remainingDebt = creditDebtSum
        val payments = IntRange(1, creditTerm).map { monthNumber ->
            val percentPart = remainingDebt * p
            val debtPart = payment - percentPart
            remainingDebt -= debtPart
            if (remainingDebt < 0) {
                remainingDebt = 0.0
            }
            CreditCalculationPaymentEntity(
                monthNumber,
                payment,
                percentPart,
                debtPart,
                remainingDebt
            )
        }
        return CreditCalculationEntity(
            CreditCalculationType.ANNUITY,
            creditDebtSum,
            creditRate,
            creditTerm,
            payments
        )
    }

    fun getDifferentiatedCalculation(
        creditDebtSum: Double,
        creditRate: Double,
        creditTerm: Int
    ): CreditCalculationEntity {
        val p = creditRate / TOTAL_PERCENT / MONTH_IN_YEAR
        var remainingDebt = creditDebtSum
        val payments = IntRange(1, creditTerm).map { monthNumber ->
            val debtPart = creditDebtSum / creditTerm
            val percentPart = remainingDebt * p
            remainingDebt -= debtPart
            if (remainingDebt < 0) {
                remainingDebt = 0.0
            }
            val payment = debtPart + percentPart
            CreditCalculationPaymentEntity(
                monthNumber,
                payment,
                percentPart,
                debtPart,
                remainingDebt
            )
        }
        return CreditCalculationEntity(
            CreditCalculationType.DIFFERENTIATED,
            creditDebtSum,
            creditRate,
            creditTerm,
            payments
        )
    }

}