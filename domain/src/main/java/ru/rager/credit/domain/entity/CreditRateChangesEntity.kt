package ru.rager.credit.domain.entity

import ru.rager.credit.domain.utils.equalsByDate
import java.util.*

open class CreditRateChangesEntity(
    val changedRate: Double,
    val changedDate: Calendar
) {
    fun isChangedRate(date: Calendar): Boolean {
        return date.equalsByDate(changedDate)
    }
}