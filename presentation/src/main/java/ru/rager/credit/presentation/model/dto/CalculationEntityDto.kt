package ru.rager.credit.presentation.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import javax.inject.Provider

@Parcelize
data class CalculationEntityDto(
    val creditCalculationId: Long? = null,
    val creditCalculationName: String? = null,
    val creditCalculationType: CreditCalculationType,
    val creditCalculationParameter: CalculationParameterEntityDto,
    val creditPaymentList: List<CalculationPaymentEntityDto>
) : Parcelable, Provider<CreditCalculationEntity> {

    constructor(creditCalculation: CreditCalculationEntity) : this(
        creditCalculationId = when (creditCalculation) {
            is CreditCalculationEntity.SavedCreditCalculationEntity -> creditCalculation.creditCalculationId
            else -> null
        },
        creditCalculationName = when (creditCalculation) {
            is CreditCalculationEntity.SavedCreditCalculationEntity -> creditCalculation.creditCalculationName
            else -> null
        },
        creditCalculationType = creditCalculation.creditCalculationType,
        creditCalculationParameter = CalculationParameterEntityDto(creditCalculation.creditCalculationParameter),
        creditPaymentList = creditCalculation.creditCalculationPaymentList.map { CalculationPaymentEntityDto(it) }
    )

    override fun get(): CreditCalculationEntity = when {
        creditCalculationId != null && creditCalculationName != null -> CreditCalculationEntity.SavedCreditCalculationEntity(
            creditCalculationId = creditCalculationId,
            creditCalculationName = creditCalculationName,
            creditCalculationType = creditCalculationType,
            creditCalculationParameter = creditCalculationParameter.get(),
            creditCalculationPaymentList = creditPaymentList.map { it.get() }
        )
        else -> CreditCalculationEntity.TempCreditCalculationEntity(
            creditCalculationType = creditCalculationType,
            creditCalculationParameter = creditCalculationParameter.get(),
            creditCalculationPaymentList = creditPaymentList.map { it.get() }
        )
    }

}