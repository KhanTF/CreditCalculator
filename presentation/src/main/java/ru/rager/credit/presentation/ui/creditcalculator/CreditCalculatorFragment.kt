package ru.rager.credit.presentation.ui.creditcalculator

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.databinding.FragmentCreditCalculatorBinding
import ru.rager.credit.presentation.ui.base.BaseFragment
import ru.rager.credit.presentation.ui.base.ViewModelEvent
import ru.rager.credit.presentation.ui.dialog.datepicker.DatePickerDialogResult
import ru.rager.credit.presentation.util.itemdecorations.LinearSpaceItemDecoration
import java.util.*

class CreditCalculatorFragment : BaseFragment<CreditCalculatorViewModel, FragmentCreditCalculatorBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.creditTermTemplateList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.creditTermTemplateList.addItemDecoration(
            LinearSpaceItemDecoration(
                start = R.dimen.dp_16,
                end = R.dimen.dp_16,
                space = R.dimen.dp_4,
                top = R.dimen.dp_0,
                bottom = R.dimen.dp_0,
                orientation = LinearLayoutManager.HORIZONTAL
            )
        )
    }

}