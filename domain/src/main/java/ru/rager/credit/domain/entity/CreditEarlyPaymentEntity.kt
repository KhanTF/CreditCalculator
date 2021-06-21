package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.EarlyPaymentType
import ru.rager.credit.domain.utils.afterByDate
import ru.rager.credit.domain.utils.beforeByDate
import ru.rager.credit.domain.utils.compareToByDate
import ru.rager.credit.domain.utils.equalsByDate
import java.util.*

open class CreditEarlyPaymentEntity(
    val earlyPaymentType: EarlyPaymentType,
    val earlyPaymentSum: Double,
    val earlyPaymentStart: Calendar,
    val earlyPaymentEnd: Calendar?,
    val earlyPeriod: CreditPeriodType,
) {
    fun isEarlyPaymentDate(date: Calendar): Boolean {
        return date.compareToByDate(earlyPaymentStart) >= 0 && (earlyPaymentEnd == null || date.compareToByDate(earlyPaymentEnd) <= 0)
    }
}