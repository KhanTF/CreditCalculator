package ru.rager.credit.domain.usecase

import io.reactivex.Completable
import io.reactivex.Single
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.SavedCreditCalculationParameterEntity
import ru.rager.credit.domain.repository.CreditCalculationParameterRepository
import javax.inject.Inject

class CalculationParameterUseCase @Inject constructor(private val creditCalculationParameterRepository: CreditCalculationParameterRepository) {

    fun getAll() = creditCalculationParameterRepository.getAll()

    fun get(id: Long) = creditCalculationParameterRepository.get(id)

    fun save(name: String, creditCalculationEntity: CreditCalculationParameterEntity): Single<SavedCreditCalculationParameterEntity> {
        return creditCalculationParameterRepository.save(
            SavedCreditCalculationParameterEntity(
                creditCalculationParameterName = name,
                creditCalculationParameterEntity = creditCalculationEntity
            )
        )
    }

    fun remove(id: Long): Completable {
        return creditCalculationParameterRepository.remove(id)
    }

}