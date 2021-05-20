package ru.rager.credit.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.entity.enums.CreditRateType

interface CalculationParameterRepository {

    fun getAll(): Observable<CreditCalculationParameterEntity.SavedCalculationParameterEntity>

    fun get(id: Long): Single<CreditCalculationParameterEntity.SavedCalculationParameterEntity>

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