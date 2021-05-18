package ru.rager.credit.presentation.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import javax.inject.Provider

@Parcelize
data class CalculationParameterEntityDto(
    private val creditSum: Double,
    private val creditRate: Double,
    private val creditTerm: Int,
) : Parcelable, Provider<CreditCalculationParameterEntity> {

    constructor(creditCalculationParameterEntity: CreditCalculationParameterEntity) : this(
        creditSum = creditCalculationParameterEntity.creditSum,
        creditRate = creditCalculationParameterEntity.creditRate,
        creditTerm = creditCalculationParameterEntity.creditTerm
    )

    override fun get(): CreditCalculationParameterEntity {
        return CreditCalculationParameterEntity(
            creditSum = creditSum,
            creditRate = creditRate,
            creditTerm = creditTerm
        )
    }

}