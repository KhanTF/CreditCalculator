package ru.rager.credit.presentation.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_save_calculation.*
import ru.rager.credit.presentation.R

class SaveCalculationDialogFragment : DialogFragment() {

    companion object {
        fun getInstance() = SaveCalculationDialogFragment()
    }

    interface Callback {
        fun onSave(name: String)
    }

    private var callback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = (parentFragment as? Callback) ?: (activity as? Callback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_save_calculation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name_edit_text_layout.isErrorEnabled = true
        name_edit_text.addTextChangedListener {
            name_edit_text_layout.error = null
        }
        save.setOnClickListener {
            val name = name_edit_text.text.toString()
            if (name.isBlank()) {
                name_edit_text_layout.error = getString(R.string.invalid_calculation_name)
            } else {
                callback?.onSave(name)
                dismiss()
            }
        }
    }

}