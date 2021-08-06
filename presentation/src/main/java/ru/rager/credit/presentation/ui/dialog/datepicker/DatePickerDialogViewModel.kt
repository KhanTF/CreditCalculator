package ru.rager.credit.presentation.ui.dialog.datepicker

import androidx.lifecycle.MutableLiveData
import ru.rager.credit.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class DatePickerDialogViewModel @Inject constructor(
    arguments: DatePickerDialogFragmentArgs
) : BaseViewModel() {

    val minDate = MutableLiveData(arguments.minDate)
    val maxDate = MutableLiveData(arguments.maxDate)
    val selectedDate = MutableLiveData(arguments.initDate)

    fun onConfirm() {
        val date = selectedDate.value
        if (date == null) {
            close()
        } else {
            postViewModelResult(DatePickerDialogResult(date))
            close()
        }
    }

}