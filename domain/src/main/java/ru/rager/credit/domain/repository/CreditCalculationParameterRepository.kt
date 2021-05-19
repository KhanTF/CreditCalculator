package ru.rager.credit.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.SavedCreditCalculationParameterEntity

interface CreditCalculationParameterRepository {

    fun getAll(): Observable<SavedCreditCalculationParameterEntity>

    fun get(id: Long): Single<SavedCreditCalculationParameterEntity>

    fun save(savedCreditCalculationParameterEntity: SavedCreditCalculationParameterEntity): Single<SavedCreditCalculationParameterEntity>

    fun remove(id: Long): Completable

}