package ru.rager.credit.presentation.util.binding.adapters

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter

object ViewAdapter {

    @BindingAdapter("onClickAndHideKeyboard")
    @JvmStatic
    fun setOnClickAndHideKeyboard(view: View, onClickListener: View.OnClickListener) {
        view.setOnClickListener {
            hideKeyboard(it)
            onClickListener.onClick(it)
        }
    }

    @BindingAdapter("isVisible")
    @JvmStatic
    fun setIsVisible(view: View, isVisible: Boolean) {
        view.visibility = when {
            isVisible -> View.VISIBLE
            else -> View.GONE
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