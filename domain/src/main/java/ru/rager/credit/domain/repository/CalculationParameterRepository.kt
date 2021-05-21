package ru.rager.credit.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.entity.enums.CreditRateType

interface CalculationParameterRepository {

    fun getAll(): Observable<List<CreditCalculationParameterEntity.SavedCalculationParameterEntity>>

    fun getAllSingle(): Single<List<CreditCalculationParameterEntity.SavedCalculationParameterEntity>>

    fun get(id: Long): Observable<CreditCalculationParameterEntity.SavedCalculationParameterEntity>

    fun getSingle(id: Long): Single<CreditCalculationParameterEntity.SavedCalculationParameterEntity>

    fun save(
        name: String,
        creditRateType: CreditRateType,
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Single<CreditCalculationParameterEntity.SavedCalculationParameterEntity>

    fun remove(id: Long): Completable

}