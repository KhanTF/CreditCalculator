package ru.rager.credit.domain.usecase

import ru.rager.credit.domain.repository.CalculationParameterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveCalculationParameterUseCase @Inject constructor(private val calculationParameterRepository: CalculationParameterRepository) {

    fun remove(id: Long) = calculationParameterRepository.remove(id)

}
