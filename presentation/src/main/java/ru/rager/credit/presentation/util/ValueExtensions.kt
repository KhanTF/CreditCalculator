package ru.rager.credit.presentation.util

import androidx.lifecycle.LiveData

const val EMPTY_STRING = ""
const val NEGATIVE_DOUBLE = Double.NEGATIVE_INFINITY
const val NEGATIVE_INT = Int.MIN_VALUE

fun Double.zeroIfNegative() = if (this < 0) {
    0
} else {
    this
}

fun LiveData<String>.getDoubleValue(): Double? {
    return value?.toDoubleOrNull()
}

fun LiveData<String>.getIntValue(): Int? {
    return value?.toIntOrNull()
}