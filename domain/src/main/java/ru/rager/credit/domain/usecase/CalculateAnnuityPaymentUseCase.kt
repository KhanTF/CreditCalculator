package ru.rager.credit.domain.usecase

import io.reactivex.Single
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import ru.rager.credit.domain.entity.CreditCalculationPaymentEntity
import ru.rager.credit.domain.utils.CreditConstants
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.pow

class CalculateAnnuityPaymentUseCase @Inject constructor() {

    fun getAnnuityCalculation(
        creditDebtSum: Double,
        creditRate: Double,
        creditTerm: Int
    ): Single<CreditCalculationEntity.TempCreditCalculationEntity> = Single.fromCallable {
        CreditCalculationEntity.TempCreditCalculationEntity(
            creditCalculationType = CreditCalculationType.ANNUITY,
            creditCalculationParameter = CreditCalculationParameterEntity(
                creditSum = creditDebtSum,
                creditRate = creditRate,
                creditTerm = creditTerm
            ),
            creditCalculationPaymentList = getAnnuityPayments(
                creditDebtSum = creditDebtSum,
                creditRate = creditRate,
                creditTerm = creditTerm
            )
        )
    }

    private fun getAnnuityPayments(
        creditDebtSum: Double,
        creditRate: Double,
        creditTerm: Int
    ): List<CreditCalculationPaymentEntity> {
        val p = CreditConstants.getP(creditRate)
        var remainingDebt = creditDebtSum
        val payment = when {
            creditRate <= 0 -> creditDebtSum / creditTerm
            else -> creditDebtSum * (p + p / ((1 + p).pow(creditTerm) - 1))
        }
        return IntRange(1, creditTerm).map { monthNumber ->
            val percentPart = remainingDebt * p
            val debtPart = payment - percentPart
            remainingDebt -= debtPart
            CreditCalculationPaymentEntity(
                creditPaymentOrder = monthNumber,
                creditPayment = payment,
                creditPercentPartOfPayment = percentPart,
                creditDebtPartOfPayment = debtPart,
                creditDebtReminder = max(remainingDebt, 0.0)
            )
        }
    }

}