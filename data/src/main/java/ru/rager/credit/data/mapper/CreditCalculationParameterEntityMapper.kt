package ru.rager.credit.data.mapper

import ru.rager.credit.data.db.entity.CalculationParameterDbEntity
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity

object CreditCalculationParameterEntityMapper {

    fun map(calculationParameterSavedEntity: CreditCalculationParameterEntity.SavedCalculationParameterEntity) = CalculationParameterDbEntity(
        creditCalculationParameterId = calculationParameterSavedEntity.creditCalculationParameterId,
        creditCalculationParameterName = calculationParameterSavedEntity.creditCalculationParameterName,
        creditRateType = calculationParameterSavedEntity.creditRateType,
        creditSum = calculationParameterSavedEntity.creditSum,
        creditRate = calculationParameterSavedEntity.creditRate,
        creditTerm = calculationParameterSavedEntity.creditTerm,
        creditRateFrequencyType = calculationParameterSavedEntity.creditRateFrequency,
        creditPaymentFrequencyType = calculationParameterSavedEntity.creditPaymentFrequency
    )

    fun map(calculationParameterDbEntity: CalculationParameterDbEntity) = CreditCalculationParameterEntity.SavedCalculationParameterEntity(
        creditCalculationParameterId = calculationParameterDbEntity.creditCalculationParameterId,
        creditCalculationParameterName = calculationParameterDbEntity.creditCalculationParameterName,
        creditRateType = calculationParameterDbEntity.creditRateType,
        creditSum = calculationParameterDbEntity.creditSum,
        creditRate = calculationParameterDbEntity.creditRate,
        creditTerm = calculationParameterDbEntity.creditTerm,
        creditRateFrequency = calculationParameterDbEntity.creditRateFrequencyType,
        creditPaymentFrequency = calculationParameterDbEntity.creditPaymentFrequencyType
    )

}