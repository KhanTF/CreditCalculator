package ru.rager.credit.domain.usecase

import ru.rager.credit.domain.entity.CreditPaymentEntity
import javax.inject.Inject
import kotlin.math.pow

class GetPaymentScheduleUseCase @Inject constructor() {

    companion object {
        private const val TOTAL_MONTH = 12
        private const val TOTAL_PERCENT = 100
    }

    private fun pow(value: Double, e: Int): Double {
        if (e <= 0) {
            return 1.0
        }
        var result = value
        for (i in 0 until e - 1) {
            result *= value
        }
        return result
    }

    fun getCreditPaymentListForAnnuity(creditAmount: Double, creditRate: Double, creditTerm: Int): List<CreditPaymentEntity> {
        val p = creditRate / TOTAL_PERCENT / TOTAL_MONTH
        val payment = creditAmount * (p + p / (pow(1 + p, creditTerm) - 1))
        var remainingDebt = creditAmount
        return IntRange(1, creditTerm).map { monthNumber ->
            val percentPart = remainingDebt * p
            val debtPart = payment - percentPart
            remainingDebt -= debtPart
            CreditPaymentEntity(
                monthNumber,
                payment,
                remainingDebt,
                percentPart,
                debtPart
            )
        }
    }

    fun getCreditPaymentListForDifferential(creditAmount: Double, creditRate: Double, creditTerm: Int): List<CreditPaymentEntity> {
        val p = creditRate / TOTAL_PERCENT / TOTAL_MONTH
        var remainingDebt = creditAmount
        return IntRange(1, creditTerm).map { monthNumber ->
            val debtPart = creditAmount / creditTerm
            val percentPart = remainingDebt * p
            val payment = debtPart + percentPart
            val entity = CreditPaymentEntity(
                monthNumber,
                payment,
                remainingDebt,
                percentPart,
                debtPart
            )
            remainingDebt -= debtPart
            entity
        }
    }

}