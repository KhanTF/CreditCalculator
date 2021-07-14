package ru.rager.credit.presentation.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.databinding.DialogCreateCalculationBinding

class CreateCalculationDialogFragment : DialogFragment() {

    companion object {
        fun getInstance() = CreateCalculationDialogFragment()
    }

    interface Callback {
        fun onSave(name: String)
    }

    private lateinit var callback: Callback
    private lateinit var binding: DialogCreateCalculationBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = (parentFragment as? Callback) ?: (activity as? Callback) ?: throw IllegalArgumentException("Callback must be implemented")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogCreateCalculationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nameEditTextLayout.isErrorEnabled = true
        binding.nameEditText.addTextChangedListener {
            binding.nameEditTextLayout.error = null
        }
        binding.save.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            if (name.isBlank()) {
                binding.nameEditTextLayout.error = getString(R.string.invalid_calculation_name)
            } else {
                callback.onSave(name)
                dismiss()
            }
        }
    }

}