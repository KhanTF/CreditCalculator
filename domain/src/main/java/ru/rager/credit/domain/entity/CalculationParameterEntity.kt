package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.entity.enums.CreditRateType

sealed class CreditCalculationParameterEntity(
    val creditRateType: CreditRateType,
    val creditSum: Double,
    val creditRate: Double,
    val creditTerm: Int,
    val creditRateFrequency: CreditFrequencyType,
    val creditPaymentFrequency: CreditFrequencyType
) {
    class TempCreditCalculationParameterEntity(
        creditRateType: CreditRateType,
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ) : CreditCalculationParameterEntity(
        creditRateType = creditRateType,
        creditSum = creditSum,
        creditRate = creditRate,
        creditTerm = creditTerm,
        creditRateFrequency = creditRateFrequency,
        creditPaymentFrequency = creditPaymentFrequency
    )

    class SavedCalculationParameterEntity(
        val creditCalculationParameterId: Long = 0L,
        val creditCalculationParameterName: String,
        creditRateType: CreditRateType,
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ) : CreditCalculationParameterEntity(
        creditRateType = creditRateType,
        creditSum = creditSum,
        creditRate = creditRate,
        creditTerm = creditTerm,
        creditRateFrequency = creditRateFrequency,
        creditPaymentFrequency = creditPaymentFrequency
    )

}
