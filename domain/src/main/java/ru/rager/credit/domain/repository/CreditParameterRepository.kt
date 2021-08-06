package ru.rager.credit.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import ru.rager.credit.domain.entity.CreditPretermPaymentEntity
import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.domain.entity.CreditRateChangeEntity
import ru.rager.credit.domain.entity.enums.PeriodType
import ru.rager.credit.domain.entity.enums.RateType
import java.util.*

interface CreditParameterRepository {

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
    ): Completable

    fun get(id: Long): Observable<CreditParametersEntity>

    fun getAll(): Observable<List<CreditParametersEntity>>

    fun delete(id: Long): Completable

}