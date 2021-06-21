package ru.rager.credit.presentation.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditRateChangesEntity
import java.util.*

@Parcelize
data class CreditRateChangesParcelable(
    private val changedRateInternal: Double,
    private val changedDateInternal: Calendar
) : CreditRateChangesEntity(
    changedRateInternal,
    changedDateInternal
), Parcelable {

    constructor(creditRateChangesEntity: CreditRateChangesEntity) : this(
        changedRateInternal = creditRateChangesEntity.changedRate,
        changedDateInternal = creditRateChangesEntity.changedDate
    )

}