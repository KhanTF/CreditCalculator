package ru.rager.credit.domain.usecase

import ru.rager.credit.domain.repository.CalculationParameterRepository
import javax.inject.Inject

class GetCalculationParameterUseCase @Inject constructor(private val calculationParameterRepository: CalculationParameterRepository) {

    fun getAll() = calculationParameterRepository.getAll()

    fun get(id: Long) = calculationParameterRepository.get(id)

}