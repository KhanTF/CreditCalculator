package ru.rager.credit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.rager.credit.data.db.dao.CalculationParameterDao
import ru.rager.credit.data.db.entity.CalculationParameterDbEntity

@Database(
    entities = [
        CalculationParameterDbEntity::class
    ],
    version = CreditDatabase.VERSION_1
)
@TypeConverters(value = [CreditDatabaseConverter::class])
abstract class CreditDatabase : RoomDatabase() {

    companion object {
        const val VERSION_1 = 1
    }

    abstract fun getCalculationPaymentDao(): CalculationParameterDao

}