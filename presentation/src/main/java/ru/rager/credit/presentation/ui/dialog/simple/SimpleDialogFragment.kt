package ru.rager.credit.presentation.ui.dialog.simple

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import ru.rager.credit.presentation.databinding.DialogSimpleBinding
import ru.rager.credit.presentation.ui.base.BaseDialogFragment

class SimpleDialogFragment : BaseDialogFragment<SimpleDialogViewModel, DialogSimpleBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}