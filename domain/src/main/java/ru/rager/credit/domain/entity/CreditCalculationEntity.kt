package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.EarlyPaymentType
import java.util.*

sealed class CreditCalculationEntity(
    val calculationDate: Calendar,
    val currentCreditRemainingDebtSum: Double,
    val currentCreditAccruedPercentSum: Double
) {
    class SchedulePaymentCreditCalculationEntity(
        val paidPercentSum: Double,
        val paidDebtSum: Double,
        calculationDate: Calendar,
        currentCreditRemainingDebtSum: Double,
        currentCreditAccruedPercentSum: Double
    ) : CreditCalculationEntity(
        calculationDate = calculationDate,
        currentCreditRemainingDebtSum = currentCreditRemainingDebtSum,
        currentCreditAccruedPercentSum = currentCreditAccruedPercentSum
    ) {
        val payment = paidPercentSum + paidDebtSum
    }

    class EarlyPaymentCreditCalculationEntity(
        val earlyPaymentType: EarlyPaymentType,
        val earlyPaidPercentSum: Double,
        val earlyPaidDebtSum: Double,
        calculationDate: Calendar,
        currentCreditRemainingDebtSum: Double,
        currentCreditAccruedPercentSum: Double
    ) : CreditCalculationEntity(
        calculationDate = calculationDate,
        currentCreditRemainingDebtSum = currentCreditRemainingDebtSum,
        currentCreditAccruedPercentSum = currentCreditAccruedPercentSum
    ) {
        val payment = earlyPaidPercentSum + earlyPaidDebtSum
    }

    class RateChangesCreditCalculationEntity(
        val changedCreditRate: Double,
        calculationDate: Calendar,
        currentCreditRemainingDebtSum: Double,
        currentCreditAccruedPercentSum: Double
    ) : CreditCalculationEntity(
        calculationDate = calculationDate,
        currentCreditRemainingDebtSum = currentCreditRemainingDebtSum,
        currentCreditAccruedPercentSum = currentCreditAccruedPercentSum
    )

}