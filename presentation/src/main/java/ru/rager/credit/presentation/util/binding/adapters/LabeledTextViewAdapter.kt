package ru.rager.credit.presentation.util.binding.adapters

import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods
import ru.rager.credit.presentation.view.LabeledTextView

@BindingMethods(
    value = [
        BindingMethod(type = LabeledTextView::class, attribute = "android:text", method = "setText")
    ]
)
@InverseBindingMethods(
    value = [
        InverseBindingMethod(type = LabeledTextView::class, attribute = "android:text", method = "getText")
    ]
)
object LabeledTextViewAdapter