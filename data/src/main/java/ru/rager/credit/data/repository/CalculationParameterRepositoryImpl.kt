package ru.rager.credit.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.rager.credit.data.db.dao.CalculationParameterDao
import ru.rager.credit.data.db.entity.CalculationParameterDbEntity
import ru.rager.credit.data.mapper.CreditCalculationParameterEntityMapper
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.exceptions.CalculationParameterAlreadyExistException
import ru.rager.credit.domain.repository.CalculationParameterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalculationParameterRepositoryImpl @Inject constructor(
    private val calculationParameterDao: CalculationParameterDao
) : CalculationParameterRepository {

    override fun getAll() = calculationParameterDao.getAll().map(CreditCalculationParameterEntityMapper::mapList)

    override fun getAllSingle() = calculationParameterDao.getAllSingle().map(CreditCalculationParameterEntityMapper::mapList)

    override fun get(id: Long) = calculationParameterDao.getById(id).map(CreditCalculationParameterEntityMapper::map)

    override fun getSingle(id: Long) = calculationParameterDao.getByIdSingle(id).map(CreditCalculationParameterEntityMapper::map)

    override fun save(
        name: String,
        creditRateType: CreditRateType,
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Single<CreditCalculationParameterEntity.SavedCalculationParameterEntity> {
        return calculationParameterDao
            .getByNameSingle(name)
            .flatMapCompletable {
                if (it.isNotEmpty()) {
                    Completable.error(CalculationParameterAlreadyExistException())
                } else {
                    Completable.complete()
                }
            }
            .andThen(
                calculationParameterDao.insert(
                    CalculationParameterDbEntity(
                        creditCalculationParameterName = name,
                        creditRateType = creditRateType,
                        creditSum = creditSum,
                        creditRate = creditRate,
                        creditTerm = creditTerm,
                        creditRateFrequencyType = creditRateFrequency,
                        creditPaymentFrequencyType = creditPaymentFrequency
                    )
                )
            )
            .flatMap(calculationParameterDao::getByIdSingle)
            .map(CreditCalculationParameterEntityMapper::map)
    }

    override fun remove(id: Long) = calculationParameterDao
        .getByIdSingle(id)
        .flatMapCompletable(calculationParameterDao::delete)

}