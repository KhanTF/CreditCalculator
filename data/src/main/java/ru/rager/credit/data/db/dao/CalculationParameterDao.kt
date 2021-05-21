package ru.rager.credit.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single
import ru.rager.credit.data.db.entity.CalculationParameterDbEntity

@Dao
interface CalculationParameterDao : BaseDao<CalculationParameterDbEntity, Long> {

    @Query("SELECT * FROM CalculationParameterDbEntity")
    fun getAll(): Observable<List<CalculationParameterDbEntity>>

    @Query("SELECT * FROM CalculationParameterDbEntity")
    fun getAllSingle(): Single<List<CalculationParameterDbEntity>>

    @Query("SELECT * FROM CalculationParameterDbEntity WHERE creditCalculationParameterName LIKE :name")
    fun getByName(name: String): Observable<List<CalculationParameterDbEntity>>

    @Query("SELECT * FROM CalculationParameterDbEntity WHERE creditCalculationParameterName LIKE :name")
    fun getByNameSingle(name: String): Single<List<CalculationParameterDbEntity>>

    @Query("SELECT * FROM CalculationParameterDbEntity WHERE creditCalculationParameterId=:id")
    fun getById(id: Long): Observable<CalculationParameterDbEntity>

    @Query("SELECT * FROM CalculationParameterDbEntity WHERE creditCalculationParameterId=:id")
    fun getByIdSingle(id: Long): Single<CalculationParameterDbEntity>

}