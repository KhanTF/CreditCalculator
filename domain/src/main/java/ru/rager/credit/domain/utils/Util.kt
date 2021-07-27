package ru.rager.credit.domain.utils

import java.util.*

fun getDaysCount(start: Long, end: Long): Long {
    return (end - start) / (24 * 60 * 60 * 1000)
}

fun Calendar?.getDaysCount(): Int {
    if (this == null) {
        return 0
    }
    val calculateYear = get(Calendar.YEAR) - 1
    val calculateMonth = get(Calendar.MONTH) + 1
    val calculateDays = get(Calendar.DAY_OF_MONTH)
    return calculateYear * calculateMonth + calculateDays
}

fun Calendar.compareToByDate(date: Calendar) = when {
    equalsByDate(date) -> 0
    afterByDate(date) -> 1
    else -> -1
}

fun Calendar?.equalsByDate(date: Calendar?): Boolean {
    return if (this != null && date != null) {
        get(Calendar.YEAR) == date.get(Calendar.YEAR) && get(Calendar.MONTH) == date.get(Calendar.MONTH) && get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
    } else this == null && date == null
}

fun Calendar.afterByDate(date: Calendar): Boolean {
    val countMonth1 = get(Calendar.YEAR) * get(Calendar.MONTH)
    val countMonth2 = date.get(Calendar.YEAR) * date.get(Calendar.MONTH)
    return when {
        countMonth1 > countMonth2 || (countMonth1 == countMonth2 && get(Calendar.DAY_OF_MONTH) > date.get(Calendar.DAY_OF_MONTH)) -> true
        else -> false
    }
}

fun Calendar.beforeByDate(date: Calendar): Boolean {
    val countMonth1 = get(Calendar.YEAR) * get(Calendar.MONTH)
    val countMonth2 = date.get(Calendar.YEAR) * date.get(Calendar.MONTH)
    return when {
        countMonth1 < countMonth2 || (countMonth1 == countMonth2 && get(Calendar.DAY_OF_MONTH) < date.get(Calendar.DAY_OF_MONTH)) -> true
        else -> false
    }
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