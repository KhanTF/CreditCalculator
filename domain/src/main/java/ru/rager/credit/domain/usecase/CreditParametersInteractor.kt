package ru.rager.credit.domain.usecase

import ru.rager.credit.domain.entity.CreditEarlyPaymentEntity
import ru.rager.credit.domain.entity.CreditRateChangesEntity
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.entity.enums.CreditStepType
import ru.rager.credit.domain.repository.CreditParameterRepository
import java.util.*
import javax.inject.Inject

class CreditParametersInteractor @Inject constructor(private val creditParameterRepository: CreditParameterRepository) {

    fun save(
        name: String,
        creditStart: Calendar,
        creditSum: Double,
        creditRate: Double,
        creditRateType: CreditRateType,
        creditTerm: Int,
        creditSkipWeekend: Boolean,
        creditRatePeriod: CreditPeriodType,
        creditPaymentPeriod: CreditPeriodType,
        creditEarlyPaymentEntityList: List<CreditEarlyPaymentEntity>,
        creditRateChangesList: List<CreditRateChangesEntity>,
        creditStepSequence: List<CreditStepType>
    ) = creditParameterRepository.save(
        name = name,
        creditStart = creditStart,
        creditSum = creditSum,
        creditRate = creditRate,
        creditRateType = creditRateType,
        creditTerm = creditTerm,
        creditSkipWeekend = creditSkipWeekend,
        creditRatePeriod = creditRatePeriod,
        creditPaymentPeriod = creditPaymentPeriod,
        creditEarlyPaymentEntityList = creditEarlyPaymentEntityList,
        creditRateChangesList = creditRateChangesList,
        creditStepSequence = creditStepSequence
    )

    fun get(id: Long) = creditParameterRepository.get(id)

    fun getAll() = creditParameterRepository.getAll()

    fun delete(id: Long) = creditParameterRepository.delete(id)

}