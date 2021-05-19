package ru.rager.credit.presentation.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import javax.inject.Provider

@Parcelize
data class CalculationEntityDto(
    val creditCalculationParameter: CalculationParameterEntityDto,
    val creditPaymentList: List<CalculationPaymentEntityDto>
) : Parcelable, Provider<CreditCalculationEntity> {

    constructor(creditCalculation: CreditCalculationEntity) : this(
        creditCalculationParameter = CalculationParameterEntityDto(creditCalculation.creditCalculationParameter),
        creditPaymentList = creditCalculation.creditCalculationPaymentList.map { CalculationPaymentEntityDto(it) }
    )

    override fun get(): CreditCalculationEntity = CreditCalculationEntity(
        creditCalculationParameter = creditCalculationParameter.get(),
        creditCalculationPaymentList = creditPaymentList.map { it.get() }
    )

}