package ru.rager.credit.data.mapper

import ru.rager.credit.data.db.entity.CalculationParameterDbEntity
import ru.rager.credit.domain.entity.SavedCreditCalculationParameterEntity

object CreditCalculationParameterEntityMapper {

    fun map(savedCreditCalculationParameterEntity: SavedCreditCalculationParameterEntity) = CalculationParameterDbEntity(
        creditCalculationParameterId = savedCreditCalculationParameterEntity.creditCalculationParameterId,
        creditCalculationParameterName = savedCreditCalculationParameterEntity.creditCalculationParameterName,
        creditCalculationType = savedCreditCalculationParameterEntity.creditCalculationType,
        creditSum = savedCreditCalculationParameterEntity.creditSum,
        creditRate = savedCreditCalculationParameterEntity.creditRate,
        creditTerm = savedCreditCalculationParameterEntity.creditTerm
    )

    fun map(calculationParameterDbEntity: CalculationParameterDbEntity) = SavedCreditCalculationParameterEntity(
        creditCalculationParameterId = calculationParameterDbEntity.creditCalculationParameterId,
        creditCalculationParameterName = calculationParameterDbEntity.creditCalculationParameterName,
        creditCalculationType = calculationParameterDbEntity.creditCalculationType,
        creditSum = calculationParameterDbEntity.creditSum,
        creditRate = calculationParameterDbEntity.creditRate,
        creditTerm = calculationParameterDbEntity.creditTerm
    )

}