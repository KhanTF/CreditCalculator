package ru.rager.credit.presentation.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_delete_calculation.*
import ru.rager.credit.presentation.R

class DeleteCalculationDialogFragment : DialogFragment() {

    companion object {
        private const val KEY_NAME = "KEY_NAME"
        fun getInstance(name: String) = DeleteCalculationDialogFragment().apply {
            val bundle = Bundle()
            bundle.putString(KEY_NAME, name)
            arguments = bundle
        }
    }

    interface Callback {
        fun onDelete()
    }

    private var callback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = (parentFragment as? Callback) ?: (activity as? Callback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_delete_calculation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = requireArguments().getString(KEY_NAME)
        subtitle.text = getString(R.string.credit_delete_calculation_confirmation, name)
        delete.setOnClickListener {
            callback?.onDelete()
            dismiss()
        }
        cancel.setOnClickListener {
            dismiss()
        }
    }

}