package ru.rager.credit.presentation.util.binding.converters.twoway

import androidx.databinding.InverseMethod

object TwoWayBindingConverters {

    @InverseMethod("stringToPositiveDouble")
    @JvmStatic
    fun positiveDoubleToString(value: Double) : String{
        return value.toString()
    }

    @JvmStatic
    fun stringToPositiveDouble(value: String) : Double{
        val double =  value.toDoubleOrNull()
        return if(double == null || double < 0){
            0.0
        }else{
            double
        }
    }

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

    @InverseMethod("stringToInt")
    @JvmStatic
    fun intToString(new: Int): String {
        return new.toString()
    }

    @JvmStatic
    fun stringToInt(new: String): Int {
        return if (new.isBlank()) {
            0
        } else {
            new.toInt()
        }
    }

}