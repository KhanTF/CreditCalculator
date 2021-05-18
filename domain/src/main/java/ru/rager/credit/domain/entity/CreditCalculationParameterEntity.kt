package ru.rager.credit.domain.entity

open class CreditCalculationParameterEntity(
    val creditSum: Double,
    val creditRate: Double,
    val creditTerm: Int,
)