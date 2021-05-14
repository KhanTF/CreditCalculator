package ru.rager.credit.presentation.util.binding.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object ImageViewAdapter {

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageResources(view: ImageView, resId: Int) {
        view.setImageResource(resId)
    }

}