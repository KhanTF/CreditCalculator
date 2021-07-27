package ru.rager.credit.presentation.util.binding.converters.oneway

import android.content.Context
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.util.emptyString
import java.text.SimpleDateFormat
import java.util.*

object DateMapper {

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    @JvmStatic
    fun dateToString(calendar: Calendar?): String {
        if (calendar == null) return emptyString()
        return dateFormatter.format(calendar.time)
    }

    @JvmStatic
    fun dateRangeToString(context: Context, start: Calendar?, end: Calendar?): String {
        return when {
            start == null && end == null -> return emptyString()
            start == null && end != null -> context.getString(R.string.before_date, dateToString(end))
            start != null && end == null -> context.getString(R.string.from_date, dateToString(start))
            else -> context.getString(R.string.from_start_before_end_date, dateToString(start), dateToString(end))
        }
    }

}