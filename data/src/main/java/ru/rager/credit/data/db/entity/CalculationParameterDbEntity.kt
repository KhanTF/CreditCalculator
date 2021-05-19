package ru.rager.credit.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.rager.credit.domain.entity.enums.CreditCalculationType

@Entity
data class CalculationParameterDbEntity(
    @PrimaryKey(autoGenerate = true)
    val creditCalculationParameterId: Long,
    val creditCalculationParameterName: String,
    val creditCalculationType: CreditCalculationType,
    val creditSum: Double,
    val creditRate: Double,
    val creditTerm: Int,
)