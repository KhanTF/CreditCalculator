package ru.rager.credit.presentation.ui.dialog.datepicker

import android.os.Bundle
import android.view.View
import ru.rager.credit.presentation.databinding.DialogDatePickerBinding
import ru.rager.credit.presentation.ui.base.BaseDialogFragment

class DatePickerDialogFragment : BaseDialogFragment<DatePickerDialogViewModel, DialogDatePickerBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}