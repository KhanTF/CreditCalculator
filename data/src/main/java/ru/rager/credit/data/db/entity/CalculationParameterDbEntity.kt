package ru.rager.credit.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.rager.credit.domain.entity.enums.PeriodType
import ru.rager.credit.domain.entity.enums.RateType

@Entity
data class CalculationParameterDbEntity(
    @PrimaryKey(autoGenerate = true)
    val creditCalculationParameterId: Long = 0L,
    val creditCalculationParameterName: String,
    val creditRateType: RateType,
    val creditSum: Double,
    val creditRate: Double,
    val creditTerm: Int,
    val creditRatePeriodType: PeriodType,
    val creditPaymentPeriodType: PeriodType
)