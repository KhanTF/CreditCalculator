package ru.rager.credit.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.entity.enums.CreditRateType

@Entity
data class CalculationParameterDbEntity(
    @PrimaryKey(autoGenerate = true)
    val creditCalculationParameterId: Long = 0L,
    val creditCalculationParameterName: String,
    val creditRateType: CreditRateType,
    val creditSum: Double,
    val creditRate: Double,
    val creditTerm: Int,
    val creditRateFrequencyType: CreditFrequencyType,
    val creditPaymentFrequencyType: CreditFrequencyType
)