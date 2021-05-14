package ru.rager.credit.presentation.util.binding.adapters

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter

object ViewAdapter {

    @BindingAdapter("app:onClickAndHideKeyboard")
    @JvmStatic
    fun setOnClickAndHideKeyboard(view: View, onClickListener: View.OnClickListener) {
        view.setOnClickListener {
            hideKeyboard(it)
            onClickListener.onClick(it)
        }
    }

    private fun hideKeyboard(view: View){
        val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val focusedView = view.rootView.findFocus()
        val windowToken = focusedView?.windowToken
        if (windowToken != null && inputManager != null) {
            inputManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }

}