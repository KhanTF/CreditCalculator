package ru.rager.credit.presentation.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.PeriodValueEntity
import ru.rager.credit.domain.entity.enums.PeriodType

@Parcelize
data class PeriodValueParcelable(private val valueInternal: Int, private val periodInternal: PeriodType) : PeriodValueEntity(valueInternal, periodInternal), Parcelable {
    constructor(periodValueEntity: PeriodValueEntity) : this(periodValueEntity.value, periodValueEntity.period)
}