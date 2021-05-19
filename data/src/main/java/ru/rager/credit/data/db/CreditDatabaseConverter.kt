package ru.rager.credit.data.db

import androidx.room.TypeConverter
import ru.rager.credit.domain.entity.enums.CreditCalculationType

object CreditDatabaseConverter {

    private const val ANNUITY = 1
    private const val DIFFERENTIATED = 2

    @JvmStatic
    @TypeConverter
    fun fromCreditCalculationTypeToInt(creditCalculationType: CreditCalculationType): Int {
        return when (creditCalculationType) {
            CreditCalculationType.ANNUITY -> ANNUITY
            CreditCalculationType.DIFFERENTIATED -> DIFFERENTIATED
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromIntToCreditCalculationType(creditCalculationType: Int): CreditCalculationType {
        return when (creditCalculationType) {
            ANNUITY -> CreditCalculationType.ANNUITY
            DIFFERENTIATED -> CreditCalculationType.DIFFERENTIATED
            else -> throw IllegalArgumentException("Unknown credit calculation type")
        }
    }

}