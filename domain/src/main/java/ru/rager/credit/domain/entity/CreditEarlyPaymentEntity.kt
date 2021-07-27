package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.EarlyPaymentPeriodType
import ru.rager.credit.domain.entity.enums.EarlyPaymentType
import ru.rager.credit.domain.utils.compareToByDate
import ru.rager.credit.domain.utils.equalsByDate
import ru.rager.credit.domain.utils.getDaysCount
import ru.rager.credit.domain.utils.skipWeekend
import java.util.*
import kotlin.math.abs

open class CreditEarlyPaymentEntity(
    val earlyPaymentType: EarlyPaymentType,
    val earlyPaymentPeriodType: EarlyPaymentPeriodType,
    val earlyPaymentSum: Double,
    val earlyPaymentStart: Calendar,
    val earlyPaymentEnd: Calendar?
) {

    fun isRegular() = earlyPaymentPeriodType != EarlyPaymentPeriodType.SINGLE

    fun isEarlyPaymentDate(date: Calendar, isSkipWeekend: Boolean): Boolean {
        return if (earlyPaymentPeriodType == EarlyPaymentPeriodType.SINGLE) {
            isCurrentEarlyPaymentDate(date, isSkipWeekend)
        } else {
            val monthCount1 = earlyPaymentStart.get(Calendar.YEAR) * 12 + (earlyPaymentStart.get(Calendar.MONTH) + 1)
            val monthCount2 = date.get(Calendar.YEAR) * 12 + (date.get(Calendar.MONTH) + 1)
            val diffMonthCount = abs(monthCount2 - monthCount1)
            val isDateRange = date.compareToByDate(earlyPaymentStart) >= 0 && (earlyPaymentEnd == null || date.compareToByDate(earlyPaymentEnd) <= 0)
            val isPeriod = diffMonthCount == 0 && diffMonthCount.rem(earlyPaymentPeriodType.value) == 0
            if (isDateRange && isPeriod) {
                isCurrentEarlyPaymentDate(date, isSkipWeekend)
            } else {
                false
            }
        }
    }

    private fun isCurrentEarlyPaymentDate(date: Calendar, isSkipWeekend: Boolean): Boolean {
        val targetDate = earlyPaymentStart.clone() as Calendar
        targetDate.set(Calendar.MONTH, date.get(Calendar.MONTH))
        if (isSkipWeekend) {
            targetDate.skipWeekend()
        }
        return targetDate.equalsByDate(date)
    }

}