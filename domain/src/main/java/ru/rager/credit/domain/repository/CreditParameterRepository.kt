package ru.rager.credit.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import ru.rager.credit.domain.entity.CreditEarlyPaymentEntity
import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.domain.entity.CreditRateChangesEntity
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.entity.enums.CreditStepType
import java.util.*

interface CreditParameterRepository {

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
    ): Completable

    fun get(id: Long): Observable<CreditParametersEntity>

    fun getAll(): Observable<List<CreditParametersEntity>>

    fun delete(id: Long): Completable

}