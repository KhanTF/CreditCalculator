package ru.rager.credit.domain.usecase

import io.reactivex.Single
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import ru.rager.credit.domain.entity.CreditCalculationPaymentEntity
import ru.rager.credit.domain.utils.CreditConstants
import javax.inject.Inject
import kotlin.math.max

class CalculateDifferentiatedPaymentUseCase @Inject constructor() {

    fun getDifferentiatedCalculation(
        creditDebtSum: Double,
        creditRate: Double,
        creditTerm: Int
    ): Single<CreditCalculationEntity.TempCreditCalculationEntity> = Single.fromCallable {
        CreditCalculationEntity.TempCreditCalculationEntity(
            creditCalculationType = CreditCalculationType.DIFFERENTIATED,
            creditCalculationParameter = CreditCalculationParameterEntity(
                creditSum = creditDebtSum,
                creditRate = creditRate,
                creditTerm = creditTerm
            ),
            creditCalculationPaymentList = getDifferentiatedPayments(
                creditDebtSum = creditDebtSum,
                creditRate = creditRate,
                creditTerm = creditTerm
            )
        )
    }

    private fun getDifferentiatedPayments(
        creditDebtSum: Double,
        creditRate: Double,
        creditTerm: Int
    ): List<CreditCalculationPaymentEntity> {
        val p = CreditConstants.getP(creditRate)
        var remainingDebt = creditDebtSum
        return IntRange(1, creditTerm).map { monthNumber ->
            val percentPart = remainingDebt * p
            val debtPart = creditDebtSum / creditTerm
            remainingDebt -= debtPart
            CreditCalculationPaymentEntity(
                creditPaymentOrder = monthNumber,
                creditPayment = debtPart + percentPart,
                creditPercentPartOfPayment = percentPart,
                creditDebtPartOfPayment = debtPart,
                creditDebtReminder = max(remainingDebt, 0.0)
            )
        }
    }


}