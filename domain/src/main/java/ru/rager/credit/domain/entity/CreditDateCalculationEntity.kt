package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.PretermType
import java.util.*

sealed class CreditDateCalculationEntity(
    val calculationDate: Calendar,
    val currentCreditRemainingDebtSum: Double,
    val currentCreditAccruedPercentSum: Double
) {
    class SchedulePaymentCreditDateCalculationEntity(
        val paidPercentSum: Double,
        val paidDebtSum: Double,
        calculationDate: Calendar,
        currentCreditRemainingDebtSum: Double,
        currentCreditAccruedPercentSum: Double
    ) : CreditDateCalculationEntity(
        calculationDate = calculationDate,
        currentCreditRemainingDebtSum = currentCreditRemainingDebtSum,
        currentCreditAccruedPercentSum = currentCreditAccruedPercentSum
    ) {
        val payment = paidPercentSum + paidDebtSum
    }

    class EarlyPaymentCreditDateCalculationEntity(
        val pretermType: PretermType,
        val earlyPaidPercentSum: Double,
        val earlyPaidDebtSum: Double,
        calculationDate: Calendar,
        currentCreditRemainingDebtSum: Double,
        currentCreditAccruedPercentSum: Double
    ) : CreditDateCalculationEntity(
        calculationDate = calculationDate,
        currentCreditRemainingDebtSum = currentCreditRemainingDebtSum,
        currentCreditAccruedPercentSum = currentCreditAccruedPercentSum
    ) {
        val payment = earlyPaidPercentSum + earlyPaidDebtSum
    }

    class RateChangesCreditDateCalculationEntity(
        val changedCreditRate: Double,
        calculationDate: Calendar,
        currentCreditRemainingDebtSum: Double,
        currentCreditAccruedPercentSum: Double
    ) : CreditDateCalculationEntity(
        calculationDate = calculationDate,
        currentCreditRemainingDebtSum = currentCreditRemainingDebtSum,
        currentCreditAccruedPercentSum = currentCreditAccruedPercentSum
    )

}