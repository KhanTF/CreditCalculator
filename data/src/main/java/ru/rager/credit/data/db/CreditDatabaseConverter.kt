package ru.rager.credit.data.db

import androidx.room.TypeConverter
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.CreditRateType

object CreditDatabaseConverter {

    private const val ANNUITY = 1
    private const val DIFFERENTIATED = 2

    private const val EVERY_YEAR = 1
    private const val EVERY_QUARTER = 2
    private const val EVERY_MONTH = 3

    @JvmStatic
    @TypeConverter
    fun fromCreditCalculationTypeToInt(creditRateType: CreditRateType): Int {
        return when (creditRateType) {
            CreditRateType.ANNUITY -> ANNUITY
            CreditRateType.DIFFERENTIATED -> DIFFERENTIATED
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromIntToCreditCalculationType(creditCalculationType: Int): CreditRateType {
        return when (creditCalculationType) {
            ANNUITY -> CreditRateType.ANNUITY
            DIFFERENTIATED -> CreditRateType.DIFFERENTIATED
            else -> throw IllegalArgumentException("Unknown credit calculation type")
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromCreditFrequencyTypeToInt(creditPeriodType: CreditPeriodType): Int {
        return when (creditPeriodType) {
            CreditPeriodType.EVERY_YEAR -> EVERY_YEAR
            CreditPeriodType.EVERY_QUARTER -> EVERY_QUARTER
            CreditPeriodType.EVERY_MONTH -> EVERY_MONTH
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromIntToCreditFrequencyType(creditFrequencyType: Int): CreditPeriodType {
        return when (creditFrequencyType) {
            EVERY_YEAR -> CreditPeriodType.EVERY_YEAR
            EVERY_QUARTER -> CreditPeriodType.EVERY_QUARTER
            EVERY_MONTH -> CreditPeriodType.EVERY_MONTH
            else -> throw IllegalArgumentException("Unknown credit frequency type")
        }
    }

}