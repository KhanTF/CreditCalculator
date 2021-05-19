package ru.rager.credit.domain.usecase

import io.reactivex.Single
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.CreditCalculationPaymentEntity
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import ru.rager.credit.domain.exceptions.PaymentTooMuchException
import ru.rager.credit.domain.exceptions.PaymentTooSmallException
import ru.rager.credit.domain.utils.CreditConstants
import ru.rager.credit.domain.utils.CreditConstants.PERCENT_CALCULATION_FAULT
import ru.rager.credit.domain.utils.CreditConstants.PERCENT_CALCULATION_MAX_ACCURACY
import ru.rager.credit.domain.utils.CreditConstants.PERCENT_CALCULATION_START_ACCURACY
import ru.rager.credit.domain.utils.CreditConstants.PERCENT_CALCULATION_START_RATE
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow

class GetCalculationUseCase @Inject constructor() {

    fun getCreditCalculationByRate(
        creditCalculationParameterEntity: CreditCalculationParameterEntity
    ) = Single.fromCallable {
        CreditCalculationEntity(
            creditCalculationParameter = creditCalculationParameterEntity,
            creditCalculationPaymentList = when (creditCalculationParameterEntity.creditCalculationType) {
                CreditCalculationType.ANNUITY -> getAnnuityPayments(creditCalculationParameterEntity)
                CreditCalculationType.DIFFERENTIATED -> getDifferentiatedPayments(creditCalculationParameterEntity)
            }
        )
    }

    private fun getDifferentiatedPayments(
        creditCalculationParameterEntity: CreditCalculationParameterEntity
    ): List<CreditCalculationPaymentEntity> {
        val p = CreditConstants.getP(creditCalculationParameterEntity.creditRate)
        var remainingDebt = creditCalculationParameterEntity.creditSum
        return IntRange(1, creditCalculationParameterEntity.creditTerm).map { monthNumber ->
            val percentPart = remainingDebt * p
            val debtPart = creditCalculationParameterEntity.creditSum / creditCalculationParameterEntity.creditTerm
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

    private fun getAnnuityPayments(
        creditCalculationParameterEntity: CreditCalculationParameterEntity
    ): List<CreditCalculationPaymentEntity> {
        val p = CreditConstants.getP(creditCalculationParameterEntity.creditRate)
        var remainingDebt = creditCalculationParameterEntity.creditSum
        val payment = when {
            creditCalculationParameterEntity.creditRate <= 0 -> creditCalculationParameterEntity.creditSum / creditCalculationParameterEntity.creditTerm
            else -> creditCalculationParameterEntity.creditSum * (p + p / ((1 + p).pow(creditCalculationParameterEntity.creditTerm) - 1))
        }
        return IntRange(1, creditCalculationParameterEntity.creditTerm).map { monthNumber ->
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