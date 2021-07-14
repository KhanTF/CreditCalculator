package ru.rager.credit.presentation.util.binding.adapters

import android.widget.DatePicker
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import java.util.*

object DatePickerAdapter {

    @BindingAdapter("app:minDate")
    @JvmStatic
    fun setMinDate(datePicker: DatePicker, minDate: Calendar?) {
        if (minDate == null) return
        datePicker.minDate = minDate.timeInMillis
    }

    @BindingAdapter("app:maxDate")
    @JvmStatic
    fun setMaxDate(datePicker: DatePicker, maxDate: Calendar?) {
        if (maxDate == null) return
        datePicker.maxDate = maxDate.timeInMillis
    }

    @BindingAdapter("app:date")
    @JvmStatic
    fun setDate(datePicker: DatePicker, date: Calendar?) {
        if (date == null) return
        val currentYear = datePicker.year
        val currentMonth = datePicker.month
        val currentDay = datePicker.dayOfMonth
        val newYear = date.get(Calendar.YEAR)
        val newMonth = date.get(Calendar.MONTH)
        val newDay = date.get(Calendar.DAY_OF_MONTH)
        if (currentYear != newYear || currentMonth != newMonth || currentDay != newDay) {
            datePicker.updateDate(newYear, newMonth, newDay)
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "app:date", event = "onDateChangedNotifier")
    fun getDate(datePicker: DatePicker): Calendar {
        val year = datePicker.year
        val month = datePicker.month
        val day = datePicker.dayOfMonth
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar
    }

    @BindingAdapter("app:onDateChangedNotifier")
    @JvmStatic
    fun setOnDateChangedNotifier(datePicker: DatePicker, inverseBindingAdapter: InverseBindingListener?) {
        val year = datePicker.year
        val month = datePicker.month
        val day = datePicker.dayOfMonth
        datePicker.init(year, month, day) { _, _, _, _ -> inverseBindingAdapter?.onChange() }
    }

}