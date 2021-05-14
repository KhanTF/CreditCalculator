package ru.rager.credit.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditCalculationPercentType

@Parcelize
data class CalculationParameters(
    val creditCalculationPercentType: CreditCalculationPercentType,
    val creditDebtSum: Double,
    val creditPercentRate: Double,
    val creditTerm: Int
) : Parcelable