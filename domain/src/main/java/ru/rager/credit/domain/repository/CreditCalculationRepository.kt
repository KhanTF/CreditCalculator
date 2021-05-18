package ru.rager.credit.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.rager.credit.domain.entity.CreditCalculationEntity

interface CreditCalculationRepository {

    fun getAll(): Observable<CreditCalculationEntity.SavedCreditCalculationEntity>

    fun get(id: Long): Single<CreditCalculationEntity.SavedCreditCalculationEntity>

    fun save(name: String, creditCalculationEntity: CreditCalculationEntity): Single<CreditCalculationEntity.SavedCreditCalculationEntity>

    fun remove(id: Long): Completable

}