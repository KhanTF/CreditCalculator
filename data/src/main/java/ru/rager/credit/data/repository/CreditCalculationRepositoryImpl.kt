package ru.rager.credit.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.repository.CreditCalculationRepository
import javax.inject.Inject

class CreditCalculationRepositoryImpl @Inject constructor() : CreditCalculationRepository {

    override fun getAll(): Observable<CreditCalculationEntity.SavedCreditCalculationEntity> {
        TODO("Not yet implemented")
    }

    override fun get(id: Long): Single<CreditCalculationEntity.SavedCreditCalculationEntity> {
        TODO("Not yet implemented")
    }

    override fun save(name: String, creditCalculationEntity: CreditCalculationEntity): Single<CreditCalculationEntity.SavedCreditCalculationEntity> {
        TODO("Not yet implemented")
    }

    override fun remove(id: Long): Completable {
        TODO("Not yet implemented")
    }

}