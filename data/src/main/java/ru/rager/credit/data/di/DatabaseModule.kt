package ru.rager.credit.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.rager.credit.data.db.CreditDatabase
import ru.rager.credit.data.db.dao.CalculationParameterDao
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): CreditDatabase {
        return Room
            .databaseBuilder(context, CreditDatabase::class.java, "credit_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCalculationPaymentDao(database: CreditDatabase): CalculationParameterDao = database.getCalculationPaymentDao()

}