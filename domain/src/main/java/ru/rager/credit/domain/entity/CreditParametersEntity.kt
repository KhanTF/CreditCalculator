package ru.rager.credit.domain.entity

import ru.rager.credit.domain.calculator.CreditPaymentCalculator
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.entity.enums.CreditStepType
import java.util.*

open class CreditParametersEntity(
    val id: Long,
    val name: String,
    val creditStart: Calendar,
    val creditSum: Double,
    val creditRate: Double,
    val creditRateType: CreditRateType,
    val creditTerm: Int,
    val creditSkipWeekend: Boolean,
    val creditRatePeriod: CreditPeriodType,
    val creditPaymentPeriod: CreditPeriodType,
    val creditEarlyPaymentEntityList: List<CreditEarlyPaymentEntity>,
    val creditRateChangesList: List<CreditRateChangesEntity>
) {

    class Builder(
        private var id: Long = -1,
        private var name: String = "",
        private var creditStart: Calendar = Calendar.getInstance(),
        private var creditSum: Double = 0.0,
        private var creditRate: Double = 0.0,
        private var creditTerm: Int = 0,
        private var creditRateType: CreditRateType = CreditRateType.ANNUITY,
        private var creditSkipWeekend: Boolean = false,
        private var creditRatePeriod: CreditPeriodType = CreditPeriodType.EVERY_YEAR,
        private var creditPaymentPeriod: CreditPeriodType = CreditPeriodType.EVERY_MONTH,
        private var creditEarlyPaymentEntityList: List<CreditEarlyPaymentEntity> = emptyList(),
        private var creditRateChangesList: List<CreditRateChangesEntity> = emptyList()
    ) {

        fun setId(id: Long): Builder {
            this.id = id
            return this
        }

        fun setName(name: String): Builder {
            this.name = name
            return this
        }

        fun setCreditStart(creditStart: Calendar): Builder {
            this.creditStart = creditStart
            return this
        }

        fun setCreditSum(creditSum: Double): Builder {
            this.creditSum = creditSum
            return this
        }

        fun setCreditRate(creditRate: Double): Builder {
            this.creditRate = creditRate
            return this
        }

        fun setCreditRateType(creditRateType: CreditRateType): Builder {
            this.creditRateType = creditRateType
            return this
        }

        fun setCreditTerm(creditTerm: Int): Builder {
            this.creditTerm = creditTerm
            return this
        }

        fun setIsSkipWeekend(creditSkipWeekend: Boolean): Builder {
            this.creditSkipWeekend = creditSkipWeekend
            return this
        }

        fun setCreditRatePeriod(creditRatePeriod: CreditPeriodType): Builder {
            this.creditRatePeriod = creditRatePeriod
            return this
        }

        fun setCreditPaymentPeriod(creditPaymentPeriod: CreditPeriodType): Builder {
            this.creditPaymentPeriod = creditPaymentPeriod
            return this
        }

        fun setCreditEarlyPaymentList(creditEarlyPaymentEntityList: List<CreditEarlyPaymentEntity>): Builder {
            this.creditEarlyPaymentEntityList = creditEarlyPaymentEntityList
            return this
        }

        fun setCreditRateChangesList(creditRateChangesList: List<CreditRateChangesEntity>): Builder {
            this.creditRateChangesList = creditRateChangesList
            return this
        }

        fun build() = CreditParametersEntity(
            id = id,
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
            creditRateChangesList = creditRateChangesList
        )

    }

}