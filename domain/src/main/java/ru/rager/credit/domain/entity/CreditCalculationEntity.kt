package ru.rager.credit.domain.entity

data class CreditCalculationEntity(
    val creditCalculationPercentType: CreditCalculationPercentType,
    val creditSum: Double,
    val creditPercentRate: Double,
    val creditTerm: Int,
    val creditPayments: List<CreditPaymentEntity>
)