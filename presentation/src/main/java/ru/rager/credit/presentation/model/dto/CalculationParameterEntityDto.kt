package ru.rager.credit.presentation.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.entity.enums.CreditRateType
import javax.inject.Provider

@Parcelize
data class CalculationParameterEntityDto(
    private val creditCalculationParameterId: Long? = null,
    private val creditCalculationParameterName: String? = null,
    private val creditRateType: CreditRateType,
    private val creditSum: Double,
    private val creditRate: Double,
    private val creditTerm: Int,
    private val creditRateFrequency: CreditFrequencyType,
    private val creditPaymentFrequency: CreditFrequencyType
) : Parcelable, Provider<CreditCalculationParameterEntity> {

    constructor(creditCalculationParameterEntity: CreditCalculationParameterEntity) : this(
        creditCalculationParameterId = when (creditCalculationParameterEntity) {
            is CreditCalculationParameterEntity.SavedCalculationParameterEntity -> creditCalculationParameterEntity.creditCalculationParameterId
            else -> null
        },
        creditCalculationParameterName = when (creditCalculationParameterEntity) {
            is CreditCalculationParameterEntity.SavedCalculationParameterEntity -> creditCalculationParameterEntity.creditCalculationParameterName
            else -> null
        },
        creditRateType = creditCalculationParameterEntity.creditRateType,
        creditSum = creditCalculationParameterEntity.creditSum,
        creditRate = creditCalculationParameterEntity.creditRate,
        creditTerm = creditCalculationParameterEntity.creditTerm,
        creditRateFrequency = creditCalculationParameterEntity.creditRateFrequency,
        creditPaymentFrequency = creditCalculationParameterEntity.creditPaymentFrequency
    )

    override fun get(): CreditCalculationParameterEntity {
        return when {
            creditCalculationParameterId != null && creditCalculationParameterName != null -> CreditCalculationParameterEntity.SavedCalculationParameterEntity(
                creditCalculationParameterId = creditCalculationParameterId,
                creditCalculationParameterName = creditCalculationParameterName,
                creditRateType = creditRateType,
                creditSum = creditSum,
                creditRate = creditRate,
                creditTerm = creditTerm,
                creditRateFrequency = creditRateFrequency,
                creditPaymentFrequency = creditPaymentFrequency
            )
            else -> CreditCalculationParameterEntity.TempCreditCalculationParameterEntity(
                creditRateType = creditRateType,
                creditSum = creditSum,
                creditRate = creditRate,
                creditTerm = creditTerm,
                creditRateFrequency = creditRateFrequency,
                creditPaymentFrequency = creditPaymentFrequency
            )
        }
    }

}