package ru.rager.credit.presentation.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.domain.entity.enums.PeriodType
import ru.rager.credit.domain.entity.enums.RateType
import java.util.*

@Parcelize
data class CreditParametersParcelable constructor(
    private val idInternal: Long?,
    private val nameInternal: String?,
    private val creditStartInternal: Calendar,
    private val creditSumInternal: Double,
    private val creditRateInternal: Double,
    private val creditRateTypeInternal: RateType,
    private val creditTermInternal: Int,
    private val creditSkipWeekendInternal: Boolean,
    private val creditRatePeriodInternal: PeriodType,
    private val creditPaymentPeriodInternal: PeriodType,
    private val creditEarlyPaymentEntityListInternal: List<CreditPretermPaymentParcelable>,
    private val creditRateChangesListInternal: List<CreditRateChangeParcelable>
) : CreditParametersEntity(
    id = idInternal ?: -1,
    name = nameInternal.orEmpty(),
    creditStart = creditStartInternal,
    creditSum = creditSumInternal,
    creditRate = creditRateInternal,
    creditRateType = creditRateTypeInternal,
    creditTerm = creditTermInternal,
    creditSkipWeekend = creditSkipWeekendInternal,
    creditRatePeriod = creditRatePeriodInternal,
    creditPaymentPeriod = creditPaymentPeriodInternal,
    creditPretermPaymentEntityList = creditEarlyPaymentEntityListInternal,
    creditRateChangeList = creditRateChangesListInternal
), Parcelable {

    constructor(creditParametersEntity: CreditParametersEntity) : this(
        creditParametersEntity.id,
        creditParametersEntity.name,
        creditParametersEntity.creditStart,
        creditParametersEntity.creditSum,
        creditParametersEntity.creditRate,
        creditParametersEntity.creditRateType,
        creditParametersEntity.creditTerm,
        creditParametersEntity.creditSkipWeekend,
        creditParametersEntity.creditRatePeriod,
        creditParametersEntity.creditPaymentPeriod,
        creditParametersEntity.creditPretermPaymentEntityList.map(::CreditPretermPaymentParcelable),
        creditParametersEntity.creditRateChangeList.map(::CreditRateChangeParcelable)
    )

}