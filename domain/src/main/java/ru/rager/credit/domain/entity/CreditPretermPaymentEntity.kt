package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.PretermType
import ru.rager.credit.domain.utils.equalsByDate
import ru.rager.credit.domain.utils.skipWeekend
import java.util.*

open class CreditPretermPaymentEntity(
    val type: PretermType,
    val periodValue: PeriodValueEntity,
    val paymentSum: Double,
    val start: Calendar,
    val end: Calendar?
) {

    fun isRegular() = !periodValue.isSingle()

    fun isEarlyPaymentDate(date: Calendar, isSkipWeekend: Boolean): Boolean {
        return false
        /* return if (monthPeriod == 0) {
             isCurrentEarlyPaymentDate(date, isSkipWeekend)
         } else {
             val monthCount1 = start.get(Calendar.YEAR) * 12 + (start.get(Calendar.MONTH) + 1)
             val monthCount2 = date.get(Calendar.YEAR) * 12 + (date.get(Calendar.MONTH) + 1)
             val diffMonthCount = abs(monthCount2 - monthCount1)
             val isDateRange = date.compareToByDate(start) >= 0 && (end == null || date.compareToByDate(end) <= 0)
             val isPeriod = diffMonthCount == 0 && diffMonthCount.rem(earlyPaymentPeriodType.value) == 0
             if (isDateRange && isPeriod) {
                 isCurrentEarlyPaymentDate(date, isSkipWeekend)
             } else {
                 false
             }
         }*/
    }

    private fun isCurrentEarlyPaymentDate(date: Calendar, isSkipWeekend: Boolean): Boolean {
        val targetDate = start.clone() as Calendar
        targetDate.set(Calendar.MONTH, date.get(Calendar.MONTH))
        if (isSkipWeekend) {
            targetDate.skipWeekend()
        }
        return targetDate.equalsByDate(date)
    }

}