package ru.rager.credit.presentation.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.entity.enums.CreditStepType
import java.util.*

@Parcelize
data class CreditParametersParcelable constructor(
    private val idInternal: Long?,
    private val nameInternal: String?,
    private val creditStartInternal: Calendar,
    private val creditSumInternal: Double,
    private val creditRateInternal: Double,
    private val creditRateTypeInternal: CreditRateType,
    private val creditTermInternal: Int,
    private val creditSkipWeekendInternal: Boolean,
    private val creditRatePeriodInternal: CreditPeriodType,
    private val creditPaymentPeriodInternal: CreditPeriodType,
    private val creditEarlyPaymentEntityListInternal: List<CreditEarlyPaymentParcelable>,
    private val creditRateChangesListInternal: List<CreditRateChangesParcelable>
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
    creditEarlyPaymentEntityList = creditEarlyPaymentEntityListInternal,
    creditRateChangesList = creditRateChangesListInternal
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
        creditParametersEntity.creditEarlyPaymentEntityList.map(::CreditEarlyPaymentParcelable),
        creditParametersEntity.creditRateChangesList.map(::CreditRateChangesParcelable)
    )

}