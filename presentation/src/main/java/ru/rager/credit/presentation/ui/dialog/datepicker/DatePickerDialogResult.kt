package ru.rager.credit.presentation.ui.dialog.datepicker

import kotlinx.android.parcel.Parcelize
import ru.rager.credit.presentation.ui.base.ViewModelResult
import java.util.*

@Parcelize
data class DatePickerDialogResult(val date: Calendar) : ViewModelResult()