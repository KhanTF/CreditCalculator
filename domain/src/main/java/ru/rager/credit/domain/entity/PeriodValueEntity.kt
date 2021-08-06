package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.PeriodType

open class PeriodValueEntity(val value: Int, val period: PeriodType) {

    companion object {
        fun getSingle() = PeriodValueEntity(0, PeriodType.MONTH)
    }

    fun getMonthCount(): Int = value * period.value

    fun isSingle() = value == 0

}