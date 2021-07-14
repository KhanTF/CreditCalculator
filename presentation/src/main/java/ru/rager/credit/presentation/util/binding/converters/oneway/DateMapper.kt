package ru.rager.credit.presentation.util.binding.converters.oneway

import java.text.SimpleDateFormat
import java.util.*

object DateMapper {

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    @JvmStatic
    fun dateToString(calendar: Calendar): String {
        return dateFormatter.format(calendar.time)
    }

}