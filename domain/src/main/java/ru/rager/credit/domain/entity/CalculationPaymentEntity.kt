package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.CreditFrequencyType

class CalculationPaymentEntity(
    val creditPaymentFrequency: CreditFrequencyType,
    val creditPaymentOrder: Int,
    val creditPayment: Double,
    val creditPercentPartOfPayment: Double,
    val creditDebtPartOfPayment: Double,
    val creditDebtReminder: Double
)