package ru.rager.credit.presentation.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.SavedCreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import javax.inject.Provider

@Parcelize
data class CalculationParameterEntityDto(
    private val creditCalculationParameterId: Long? = null,
    private val creditCalculationParameterName: String? = null,
    private val creditCalculationType: CreditCalculationType,
    private val creditSum: Double,
    private val creditRate: Double,
    private val creditTerm: Int,
) : Parcelable, Provider<CreditCalculationParameterEntity> {

    constructor(creditCalculationParameterEntity: CreditCalculationParameterEntity) : this(
        creditCalculationParameterId = when (creditCalculationParameterEntity) {
            is SavedCreditCalculationParameterEntity -> creditCalculationParameterEntity.creditCalculationParameterId
            else -> null
        },
        creditCalculationParameterName = when (creditCalculationParameterEntity) {
            is SavedCreditCalculationParameterEntity -> creditCalculationParameterEntity.creditCalculationParameterName
            else -> null
        },
        creditCalculationType = creditCalculationParameterEntity.creditCalculationType,
        creditSum = creditCalculationParameterEntity.creditSum,
        creditRate = creditCalculationParameterEntity.creditRate,
        creditTerm = creditCalculationParameterEntity.creditTerm
    )

    override fun get(): CreditCalculationParameterEntity {
        return when {
            creditCalculationParameterId != null && creditCalculationParameterName != null -> SavedCreditCalculationParameterEntity(
                creditCalculationParameterId = creditCalculationParameterId,
                creditCalculationParameterName = creditCalculationParameterName,
                creditCalculationType = creditCalculationType,
                creditSum = creditSum,
                creditRate = creditRate,
                creditTerm = creditTerm
            )
            else -> CreditCalculationParameterEntity(
                creditCalculationType = creditCalculationType,
                creditSum = creditSum,
                creditRate = creditRate,
                creditTerm = creditTerm
            )
        }
    }

}