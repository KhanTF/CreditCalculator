package ru.rager.credit.domain.usecase

import io.reactivex.Completable
import io.reactivex.Single
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.repository.CreditCalculationRepository
import javax.inject.Inject

class SavedCalculationUseCase @Inject constructor(private val creditCalculationRepository: CreditCalculationRepository) {

    fun getAll() = creditCalculationRepository.getAll()

    fun get(id: Long) = creditCalculationRepository.get(id)

    fun save(name: String, creditCalculationEntity: CreditCalculationEntity): Single<CreditCalculationEntity.SavedCreditCalculationEntity> {
        return creditCalculationRepository.save(name, creditCalculationEntity)
    }

    fun remove(id: Long): Completable {
        return creditCalculationRepository.remove(id)
    }

}