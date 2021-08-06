package ru.rager.credit.data.db

import androidx.room.TypeConverter
import ru.rager.credit.domain.entity.enums.PeriodType
import ru.rager.credit.domain.entity.enums.RateType

object CreditDatabaseConverter {

    private const val ANNUITY = 1
    private const val DIFFERENTIATED = 2

    private const val EVERY_YEAR = 1
    private const val EVERY_QUARTER = 2
    private const val EVERY_MONTH = 3

    @JvmStatic
    @TypeConverter
    fun fromCreditCalculationTypeToInt(creditRateType: RateType): Int {
        return when (creditRateType) {
            RateType.ANNUITY -> ANNUITY
            RateType.DIFFERENTIATED -> DIFFERENTIATED
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromIntToCreditCalculationType(creditCalculationType: Int): RateType {
        return when (creditCalculationType) {
            ANNUITY -> RateType.ANNUITY
            DIFFERENTIATED -> RateType.DIFFERENTIATED
            else -> throw IllegalArgumentException("Unknown credit calculation type")
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromCreditFrequencyTypeToInt(creditPeriodType: PeriodType): Int {
        return when (creditPeriodType) {
            PeriodType.YEAR -> EVERY_YEAR
            PeriodType.QUARTER -> EVERY_QUARTER
            PeriodType.MONTH -> EVERY_MONTH
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromIntToCreditFrequencyType(creditFrequencyType: Int): PeriodType {
        return when (creditFrequencyType) {
            EVERY_YEAR -> PeriodType.YEAR
            EVERY_QUARTER -> PeriodType.QUARTER
            EVERY_MONTH -> PeriodType.MONTH
            else -> throw IllegalArgumentException("Unknown credit frequency type")
        }
    }

}