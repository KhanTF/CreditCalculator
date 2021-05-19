package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.CreditCalculationType

open class CreditCalculationParameterEntity(
    val creditCalculationType: CreditCalculationType,
    val creditSum: Double,
    val creditRate: Double,
    val creditTerm: Int,
)

class SavedCreditCalculationParameterEntity(
    val creditCalculationParameterId: Long = 0L,
    val creditCalculationParameterName: String,
    creditCalculationType: CreditCalculationType,
    creditSum: Double,
    creditRate: Double,
    creditTerm: Int,
) : CreditCalculationParameterEntity(
    creditCalculationType = creditCalculationType,
    creditSum = creditSum,
    creditRate = creditRate,
    creditTerm = creditTerm
) {

    constructor(
        creditCalculationParameterId: Long = 0L,
        creditCalculationParameterName: String,
        creditCalculationParameterEntity: CreditCalculationParameterEntity
    ) : this(
        creditCalculationParameterId,
        creditCalculationParameterName,
        creditCalculationParameterEntity.creditCalculationType,
        creditCalculationParameterEntity.creditSum,
        creditCalculationParameterEntity.creditRate,
        creditCalculationParameterEntity.creditTerm,
    )

}