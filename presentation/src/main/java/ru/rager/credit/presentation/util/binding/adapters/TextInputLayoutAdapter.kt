package ru.rager.credit.presentation.util.binding.adapters

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object TextInputLayoutAdapter {

    @BindingAdapter("app:error", "app:errorIsVisible", requireAll = true)
    @JvmStatic
    fun setError(textInputLayout: TextInputLayout, error: String, isVisible: Boolean) {
        if (isVisible) {
            textInputLayout.error = error
        } else {
            textInputLayout.error = null
        }
    }

}