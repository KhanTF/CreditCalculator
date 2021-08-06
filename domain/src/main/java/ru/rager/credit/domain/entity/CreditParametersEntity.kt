package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.PeriodType
import ru.rager.credit.domain.entity.enums.RateType
import java.util.*

open class CreditParametersEntity(
    val id: Long,
    val name: String,
    val creditStart: Calendar,
    val creditSum: Double,
    val creditRate: Double,
    val creditRateType: RateType,
    val creditTerm: Int,
    val creditSkipWeekend: Boolean,
    val creditRatePeriod: PeriodType,
    val creditPaymentPeriod: PeriodType,
    val creditPretermPaymentEntityList: List<CreditPretermPaymentEntity>,
    val creditRateChangeList: List<CreditRateChangeEntity>
) {

    class Builder(
        private var id: Long = -1,
        private var name: String = "",
        private var creditStart: Calendar = Calendar.getInstance(),
        private var creditSum: Double = 0.0,
        private var creditRate: Double = 0.0,
        private var creditTerm: Int = 0,
        private var creditRateType: RateType = RateType.ANNUITY,
        private var creditSkipWeekend: Boolean = false,
        private var creditRatePeriod: PeriodType = PeriodType.YEAR,
        private var creditPaymentPeriod: PeriodType = PeriodType.MONTH,
        private var creditPretermPaymentEntityList: List<CreditPretermPaymentEntity> = emptyList(),
        private var creditRateChangeList: List<CreditRateChangeEntity> = emptyList()
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

        fun setCreditRateType(creditRateType: RateType): Builder {
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

        fun setCreditRatePeriod(creditRatePeriod: PeriodType): Builder {
            this.creditRatePeriod = creditRatePeriod
            return this
        }

        fun setCreditPaymentPeriod(creditPaymentPeriod: PeriodType): Builder {
            this.creditPaymentPeriod = creditPaymentPeriod
            return this
        }

        fun setCreditEarlyPaymentList(creditPretermPaymentEntityList: List<CreditPretermPaymentEntity>): Builder {
            this.creditPretermPaymentEntityList = creditPretermPaymentEntityList
            return this
        }

        fun setCreditRateChangesList(creditRateChangeList: List<CreditRateChangeEntity>): Builder {
            this.creditRateChangeList = creditRateChangeList
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
            creditPretermPaymentEntityList = creditPretermPaymentEntityList,
            creditRateChangeList = creditRateChangeList
        )

    }

}