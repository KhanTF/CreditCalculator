package ru.rager.credit.domain.usecase

import ru.rager.credit.domain.entity.CreditPretermPaymentEntity
import ru.rager.credit.domain.entity.CreditRateChangeEntity
import ru.rager.credit.domain.entity.enums.PeriodType
import ru.rager.credit.domain.entity.enums.RateType
import ru.rager.credit.domain.repository.CreditParameterRepository
import java.util.*
import javax.inject.Inject

class CreditParametersInteractor @Inject constructor(private val creditParameterRepository: CreditParameterRepository) {

    fun save(
        name: String,
        creditStart: Calendar,
        creditSum: Double,
        creditRate: Double,
        creditRateType: RateType,
        creditTerm: Int,
        creditSkipWeekend: Boolean,
        creditRatePeriod: PeriodType,
        creditPaymentPeriod: PeriodType,
        creditPretermPaymentEntityList: List<CreditPretermPaymentEntity>,
        creditRateChangeList: List<CreditRateChangeEntity>
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
        creditPretermPaymentEntityList = creditPretermPaymentEntityList,
        creditRateChangeList = creditRateChangeList
    )

    fun get(id: Long) = creditParameterRepository.get(id)

    fun getAll() = creditParameterRepository.getAll()

    fun delete(id: Long) = creditParameterRepository.delete(id)

}