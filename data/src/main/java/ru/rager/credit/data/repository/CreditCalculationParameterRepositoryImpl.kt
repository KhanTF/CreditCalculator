package ru.rager.credit.data.repository

import androidx.room.EmptyResultSetException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.rager.credit.data.db.dao.CalculationParameterDao
import ru.rager.credit.data.mapper.CreditCalculationParameterEntityMapper
import ru.rager.credit.domain.entity.SavedCreditCalculationParameterEntity
import ru.rager.credit.domain.exceptions.CalculationParameterAlreadyExistException
import ru.rager.credit.domain.repository.CreditCalculationParameterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditCalculationParameterRepositoryImpl @Inject constructor(
    private val calculationParameterDao: CalculationParameterDao
) : CreditCalculationParameterRepository {

    override fun getAll(): Observable<SavedCreditCalculationParameterEntity> {
        return calculationParameterDao.getAll().flatMapObservable { Observable.fromIterable(it) }.map(CreditCalculationParameterEntityMapper::map)
    }

    override fun get(id: Long): Single<SavedCreditCalculationParameterEntity> {
        return calculationParameterDao.getById(id).map(CreditCalculationParameterEntityMapper::map)
    }

    override fun save(savedCreditCalculationParameterEntity: SavedCreditCalculationParameterEntity): Single<SavedCreditCalculationParameterEntity> {
        return calculationParameterDao
            .getByName(savedCreditCalculationParameterEntity.creditCalculationParameterName)
            .flatMapCompletable { Completable.error(CalculationParameterAlreadyExistException()) }
            .onErrorResumeNext {
                if (it is EmptyResultSetException) {
                    Completable.complete()
                } else {
                    Completable.error(it)
                }
            }
            .andThen(calculationParameterDao.insert(CreditCalculationParameterEntityMapper.map(savedCreditCalculationParameterEntity)))
            .flatMap(calculationParameterDao::getById)
            .map(CreditCalculationParameterEntityMapper::map)
    }

    override fun remove(id: Long): Completable {
        return calculationParameterDao
            .getById(id)
            .flatMapCompletable(calculationParameterDao::delete)
    }

}