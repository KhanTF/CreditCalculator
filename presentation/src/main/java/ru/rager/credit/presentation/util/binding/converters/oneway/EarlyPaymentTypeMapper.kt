package ru.rager.credit.presentation.util.binding.converters.oneway

import android.content.Context
import ru.rager.credit.domain.entity.enums.EarlyPaymentType
import ru.rager.credit.presentation.R

object EarlyPaymentTypeMapper {

    @JvmStatic
    fun earlyPaymentToStringRes(earlyPaymentType: EarlyPaymentType): Int {
        return when (earlyPaymentType) {
            EarlyPaymentType.EARLY_DECREASE_PAYMENT -> R.string.credit_early_payment_decrease_payment
            EarlyPaymentType.EARLY_DECREASE_TERM -> R.string.credit_early_payment_decrease_term
        }
    }

    @JvmStatic
    fun earlyPaymentToString(context: Context, earlyPaymentType: EarlyPaymentType): String {
        return context.getString(earlyPaymentToStringRes(earlyPaymentType))
    }

    @JvmStatic
    fun earlyPaymentToStringRes(earlyPaymentTypeList: List<EarlyPaymentType>): List<Int> {
        return earlyPaymentTypeList.map { earlyPaymentToStringRes(it) }
    }

}