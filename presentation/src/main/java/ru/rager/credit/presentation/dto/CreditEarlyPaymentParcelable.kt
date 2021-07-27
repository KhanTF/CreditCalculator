package ru.rager.credit.presentation.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditEarlyPaymentEntity
import ru.rager.credit.domain.entity.enums.EarlyPaymentPeriodType
import ru.rager.credit.domain.entity.enums.EarlyPaymentType
import java.util.*

@Parcelize
data class CreditEarlyPaymentParcelable(
    private val earlyPaymentTypeInternal: EarlyPaymentType,
    private val earlyPaymentPeriodTypeInternal: EarlyPaymentPeriodType,
    private val earlyPaymentSumInternal: Double,
    private val earlyPaymentStartInternal: Calendar,
    private val earlyPaymentEndInternal: Calendar?
) : CreditEarlyPaymentEntity(
    earlyPaymentType = earlyPaymentTypeInternal,
    earlyPaymentSum = earlyPaymentSumInternal,
    earlyPaymentPeriodType = earlyPaymentPeriodTypeInternal,
    earlyPaymentStart = earlyPaymentStartInternal,
    earlyPaymentEnd = earlyPaymentEndInternal,
), Parcelable {

    constructor(creditEarlyPaymentEntity: CreditEarlyPaymentEntity) : this(
        creditEarlyPaymentEntity.earlyPaymentType,
        creditEarlyPaymentEntity.earlyPaymentPeriodType,
        creditEarlyPaymentEntity.earlyPaymentSum,
        creditEarlyPaymentEntity.earlyPaymentStart,
        creditEarlyPaymentEntity.earlyPaymentEnd
    )

}