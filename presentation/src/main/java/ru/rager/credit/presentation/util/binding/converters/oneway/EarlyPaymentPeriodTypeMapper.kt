package ru.rager.credit.presentation.util.binding.converters.oneway

import android.content.Context
import androidx.compose.ui.text.toLowerCase
import ru.rager.credit.domain.entity.enums.EarlyPaymentPeriodType
import ru.rager.credit.presentation.R
import java.util.*

object EarlyPaymentPeriodTypeMapper {

    @JvmStatic
    fun earlyPaymentPeriodTypeToStringRes(earlyPaymentPeriodType: EarlyPaymentPeriodType): Int {
        return when (earlyPaymentPeriodType) {
            EarlyPaymentPeriodType.SINGLE -> R.string.credit_early_payment_single
            EarlyPaymentPeriodType.EVERY_MONTH -> R.string.credit_early_payment_every_month
            EarlyPaymentPeriodType.EVERY_QUARTER -> R.string.credit_early_payment_every_quarter
            EarlyPaymentPeriodType.EVERY_HALF_YEAR -> R.string.credit_early_payment_every_half_year
            EarlyPaymentPeriodType.EVERY_YEAR -> R.string.credit_early_payment_every_year
        }
    }

    @JvmStatic
    fun earlyPaymentPeriodTypeToString(context: Context, earlyPaymentPeriodType: EarlyPaymentPeriodType): String {
        return context.getString(earlyPaymentPeriodTypeToStringRes(earlyPaymentPeriodType))
    }

    @JvmStatic
    fun earlyPaymentPeriodTypeToStringRes(earlyPaymentPeriodTypeList: List<EarlyPaymentPeriodType>): List<Int> {
        return earlyPaymentPeriodTypeList.map { earlyPaymentPeriodTypeToStringRes(it) }
    }

    @JvmStatic
    fun earlyPaymentPeriodTypeToStringRes(context: Context, earlyPaymentPeriodTypeList: List<EarlyPaymentPeriodType>): List<String> {
        return earlyPaymentPeriodTypeList.map { earlyPaymentPeriodTypeToString(context, it) }
    }

    @JvmStatic
    fun earlyPaymentPeriodTypeWithDateRangeToString(context: Context, earlyPaymentPeriodType: EarlyPaymentPeriodType, start: Calendar, end: Calendar?): String {
        return context.getString(
            R.string.extras,
            earlyPaymentPeriodTypeToString(context, earlyPaymentPeriodType),
            DateMapper.dateRangeToString(context, start, end).toLowerCase(Locale.getDefault())
        )
    }


}