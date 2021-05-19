package ru.rager.credit.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single
import ru.rager.credit.data.db.entity.CalculationParameterDbEntity

@Dao
interface CalculationParameterDao : BaseDao<CalculationParameterDbEntity, Long> {

    @Query("SELECT * FROM CalculationParameterDbEntity")
    fun getAll(): Single<List<CalculationParameterDbEntity>>

    @Query("SELECT * FROM CalculationParameterDbEntity WHERE creditCalculationParameterName LIKE :name")
    fun getByName(name: String): Single<CalculationParameterDbEntity>

    @Query("SELECT * FROM CalculationParameterDbEntity WHERE creditCalculationParameterId=:id")
    fun getById(id: Long): Single<CalculationParameterDbEntity>

    @Query("SELECT * FROM CalculationParameterDbEntity WHERE creditCalculationParameterId=:creditCalculationParameterId")
    fun getByCreditCalculationParameterId(creditCalculationParameterId: Long): Single<CalculationParameterDbEntity>

}