package ru.rager.credit.presentation.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditPretermPaymentEntity
import ru.rager.credit.domain.entity.PeriodValueEntity
import ru.rager.credit.domain.entity.enums.PeriodType
import ru.rager.credit.domain.entity.enums.PretermType
import java.util.*

@Parcelize
data class CreditPretermPaymentParcelable(
    private val typeInternal: PretermType,
    private val periodValueInternal: PeriodValueParcelable,
    private val paymentSumInternal: Double,
    private val startInternal: Calendar,
    private val endInternal: Calendar?
) : CreditPretermPaymentEntity(
    type = typeInternal,
    paymentSum = paymentSumInternal,
    periodValue = periodValueInternal,
    start = startInternal,
    end = endInternal,
), Parcelable {

    constructor(creditPretermPaymentEntity: CreditPretermPaymentEntity) : this(
        creditPretermPaymentEntity.type,
        PeriodValueParcelable(creditPretermPaymentEntity.periodValue),
        creditPretermPaymentEntity.paymentSum,
        creditPretermPaymentEntity.start,
        creditPretermPaymentEntity.end
    )

}