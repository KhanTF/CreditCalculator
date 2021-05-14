package ru.rager.credit.domain.usecase

import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationPercentType
import ru.rager.credit.domain.entity.CreditPaymentEntity
import javax.inject.Inject
import kotlin.math.pow

class GetCreditCalculationResultUseCase @Inject constructor() {

    companion object {
        private const val TOTAL_MONTH = 12
        private const val TOTAL_PERCENT = 100
    }

    fun getAnnuityCalculationResult(creditDebtSum: Double, creditRate: Double, creditTerm: Int): CreditCalculationEntity {
        val p = creditRate / TOTAL_PERCENT / TOTAL_MONTH
        val payment = creditDebtSum * (p + p / ((1 + p).pow(creditTerm) - 1))
        var remainingDebt = creditDebtSum
        val payments = IntRange(1, creditTerm).map { monthNumber ->
            val percentPart = remainingDebt * p
            val debtPart = payment - percentPart
            remainingDebt -= debtPart
            CreditPaymentEntity(
                monthNumber,
                payment,
                percentPart,
                debtPart,
                remainingDebt
            )
        }
        return CreditCalculationEntity(
            CreditCalculationPercentType.ANNUITY,
            creditDebtSum,
            creditRate,
            creditTerm,
            payments
        )
    }

    fun getDifferentiatedCalculationResult(creditDebtSum: Double, creditRate: Double, creditTerm: Int): CreditCalculationEntity {
        val p = creditRate / TOTAL_PERCENT / TOTAL_MONTH
        var remainingDebt = creditDebtSum
        val payments = IntRange(1, creditTerm).map { monthNumber ->
            val debtPart = creditDebtSum / creditTerm
            val percentPart = remainingDebt * p
            remainingDebt -= debtPart
            val payment = debtPart + percentPart
            CreditPaymentEntity(
                monthNumber,
                payment,
                remainingDebt,
                percentPart,
                debtPart
            )
        }
        return CreditCalculationEntity(
            CreditCalculationPercentType.DIFFERENTIATED,
            creditDebtSum,
            creditRate,
            creditTerm,
            payments
        )
    }

}