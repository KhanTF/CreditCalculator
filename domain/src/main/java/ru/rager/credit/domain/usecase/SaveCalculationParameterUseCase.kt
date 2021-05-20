package ru.rager.credit.domain.usecase

import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.repository.CalculationParameterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveCalculationParameterUseCase @Inject constructor(private val calculationParameterRepository: CalculationParameterRepository) {

    fun save(
        name: String,
        creditRateType: CreditRateType,
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRateFrequency: CreditFrequencyType = CreditFrequencyType.EVERY_YEAR,
        creditPaymentFrequency: CreditFrequencyType = CreditFrequencyType.EVERY_MONTH
    ) = calculationParameterRepository.save(name, creditRateType, creditSum, creditRate, creditTerm, creditRateFrequency, creditPaymentFrequency)

}