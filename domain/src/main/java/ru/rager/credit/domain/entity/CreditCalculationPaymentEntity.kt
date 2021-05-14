package ru.rager.credit.domain.entity

class CreditCalculationPaymentEntity(
    val creditMonthNumber: Int,
    val creditPayment: Double,
    val creditPercentPartOfPayment: Double,
    val creditDebtPartOfPayment: Double,
    val creditDebtReminder: Double
)