package ru.rager.credit.presentation.util

import android.content.Context
import android.widget.ArrayAdapter
import java.text.DecimalFormat
import kotlin.concurrent.getOrSet

private val decimalFormatThreadLocal = ThreadLocal<DecimalFormat>().also {
    it.set(DecimalFormat("###.###"))
}

fun emptyString() = ""

fun getArrayAdapter(context: Context, array: List<String>) = ArrayAdapter(context, android.R.layout.simple_spinner_item, array)

fun <T> Array<T>.indexOfOr(value: T?, default: Int = -1): Int {
    return when (val index = indexOf(value)) {
        -1 -> default
        else -> index
    }
}

fun <T> List<T>.indexOfOr(value: T?, default: Int = -1): Int {
    return when (val index = indexOf(value)) {
        -1 -> default
        else -> index
    }
}

