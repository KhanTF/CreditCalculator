package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.EarlyPaymentType
import ru.rager.credit.domain.utils.compareToByDate
import ru.rager.credit.domain.utils.equalsByDate
import ru.rager.credit.domain.utils.skipWeekend
import java.util.*
import kotlin.math.abs

open class CreditEarlyPaymentEntity(
    val earlyPaymentType: EarlyPaymentType,
    val earlyPaymentSum: Double,
    val earlyPaymentMonthPeriod: Int,
    val earlyPaymentStart: Calendar,
    val earlyPaymentEnd: Calendar?
) {

    fun isEarlyPaymentDate(date: Calendar, isSkipWeekend: Boolean): Boolean {
        val monthCount1 = earlyPaymentStart.get(Calendar.YEAR) * 12 + (earlyPaymentStart.get(Calendar.MONTH) + 1)
        val monthCount2 = date.get(Calendar.YEAR) * 12 + (date.get(Calendar.MONTH) + 1)
        val diffMonthCount = abs(monthCount2 - monthCount1)
        val isDateRange = date.compareToByDate(earlyPaymentStart) >= 0 && (earlyPaymentEnd == null || date.compareToByDate(earlyPaymentEnd) <= 0)
        val isPeriod = diffMonthCount == 0 && diffMonthCount.rem(earlyPaymentMonthPeriod) == 0
        return if (isDateRange && isPeriod) {
            val targetDate = earlyPaymentStart.clone() as Calendar
            targetDate.set(Calendar.MONTH, date.get(Calendar.MONTH))
            if (isSkipWeekend) {
                targetDate.skipWeekend()
            }
            targetDate.equalsByDate(date)
        } else {
            false
        }
    }

}