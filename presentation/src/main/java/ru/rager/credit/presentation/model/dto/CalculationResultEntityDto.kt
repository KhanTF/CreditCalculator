package ru.rager.credit.presentation.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import javax.inject.Provider

@Parcelize
data class CalculationResultEntityDto(
    val creditCalculationType: CreditCalculationType,
    val creditSum: Double,
    val creditRate: Double,
    val creditTerm: Int,
    val creditPaymentList: List<CalculationPaymentEntityDto>
) : Parcelable, Provider<CreditCalculationEntity> {

    constructor(creditCalculationEntity: CreditCalculationEntity) : this(
        creditCalculationType = creditCalculationEntity.creditCalculationType,
        creditSum = creditCalculationEntity.creditSum,
        creditRate = creditCalculationEntity.creditRate,
        creditTerm = creditCalculationEntity.creditTerm,
        creditPaymentList = creditCalculationEntity.creditCalculationPaymentList.map { CalculationPaymentEntityDto(it) }
    )

    override fun get(): CreditCalculationEntity {
        return CreditCalculationEntity(
            creditCalculationType = creditCalculationType,
            creditSum = creditSum,
            creditRate = creditRate,
            creditTerm = creditTerm,
            creditCalculationPaymentList = creditPaymentList.map { it.get() }
        )
    }

}