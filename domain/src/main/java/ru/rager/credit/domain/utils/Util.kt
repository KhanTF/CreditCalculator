package ru.rager.credit.domain.utils

import java.util.*

fun getDaysCount(start: Long, end: Long): Long {
    return (end - start) / (24 * 60 * 60 * 1000)
}

fun Calendar?.equalsByDate(date: Calendar?): Boolean {
    return if (this != null && date != null) {
        get(Calendar.YEAR) == date.get(Calendar.YEAR) && get(Calendar.MONTH) == date.get(Calendar.MONTH) && get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
    } else this == null && date == null
}

fun Calendar.nextDayOfMonth(): Calendar {
    val dateClone = clone() as Calendar
    dateClone.add(Calendar.DAY_OF_MONTH, 1)
    return dateClone
}

fun Calendar.skipWeekend() {
    when {
        get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY -> add(Calendar.DAY_OF_MONTH, 2)
        get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY -> add(Calendar.DAY_OF_MONTH, 1)
    }
}