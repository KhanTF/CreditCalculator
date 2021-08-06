package ru.rager.credit.presentation.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditRateChangeEntity
import java.util.*

@Parcelize
data class CreditRateChangeParcelable(
    private val changedRateInternal: Double,
    private val changedDateInternal: Calendar
) : CreditRateChangeEntity(
    changedRateInternal,
    changedDateInternal
), Parcelable {

    constructor(creditRateChangeEntity: CreditRateChangeEntity) : this(
        changedRateInternal = creditRateChangeEntity.changedRate,
        changedDateInternal = creditRateChangeEntity.changedDate
    )

}