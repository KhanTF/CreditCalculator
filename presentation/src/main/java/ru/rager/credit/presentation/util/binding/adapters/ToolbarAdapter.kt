package ru.rager.credit.presentation.util.binding.adapters

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

@BindingMethods(
    value = [
        BindingMethod(type = Toolbar::class, attribute = "onNavigationClick", method = "setNavigationOnClickListener"),
        BindingMethod(type = Toolbar::class, attribute = "onMenuItemClick", method = "setOnMenuItemClickListener")
    ]
)
object ToolbarAdapter