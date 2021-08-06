package ru.rager.credit.presentation.util.binding.converters

import android.content.Context
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.util.emptyString
import java.text.SimpleDateFormat
import java.util.*

object DateBindingConverter {

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    @JvmStatic
    fun mapCalendarToToDateString(calendar: Calendar?): String {
        if (calendar == null) return emptyString()
        return dateFormatter.format(calendar.time)
    }

    @JvmStatic
    fun mapCalendarToRangeDateString(context: Context, start: Calendar?, end: Calendar?): String {
        return when {
            start == null && end == null -> return emptyString()
            start == null && end != null -> return emptyString()
            start != null && end == null -> mapCalendarToToDateString(start)
            else -> context.getString(R.string.format_date_range, mapCalendarToToDateString(start), mapCalendarToToDateString(end))
        }
    }

}