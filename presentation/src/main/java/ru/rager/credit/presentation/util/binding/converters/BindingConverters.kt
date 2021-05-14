package ru.rager.credit.presentation.util.binding.converters

import androidx.databinding.InverseMethod

object BindingConverters {

    @InverseMethod("stringToDouble")
    @JvmStatic
    fun doubleToString(new: Double): String {
        return new.toString()
    }

    @JvmStatic
    fun stringToDouble(new: String): Double {
        return if (new.isBlank()) {
            0.0
        } else {
            new.toDouble()
        }
    }

}