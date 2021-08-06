package ru.rager.credit.presentation.util.binding.converters

import android.content.Context
import ru.rager.credit.domain.entity.PeriodValueEntity
import ru.rager.credit.domain.entity.enums.*
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.util.emptyString
import java.util.*

object EnumBindingConverter {

    @JvmStatic
    fun mapCalculatorTypeToString(context: Context, calculatorType: CalculatorType?): String {
        return when (calculatorType) {
            CalculatorType.STANDARD_CALCULATOR -> context.getString(R.string.standard_calculator)
            CalculatorType.PERCENT_CALCULATOR -> context.getString(R.string.percent_calculator)
            else -> emptyString()
        }
    }

    @JvmStatic
    fun mapPeriodTypeToRateTypeString(context: Context, period: PeriodType?): String {
        val resources = when (period) {
            PeriodType.YEAR -> R.string.credit_rate_every_year
            PeriodType.QUARTER -> R.string.credit_rate_every_quarter
            PeriodType.MONTH -> R.string.credit_rate_every_month
            else -> R.string.empty
        }
        return context.getString(resources)
    }

    @JvmStatic
    fun mapPeriodTypeToPaymentTypeString(context: Context, period: PeriodType?): String {
        val resources = when (period) {
            PeriodType.YEAR -> R.string.credit_payment_every_year
            PeriodType.QUARTER -> R.string.credit_payment_every_quarter
            PeriodType.MONTH -> R.string.credit_payment_every_month
            else -> R.string.empty
        }
        return context.getString(resources)
    }

    @JvmStatic
    fun formatPeriodTypeToPluralsString(context: Context, periodType: PeriodType?, value: Int?): String {
        if (value == null || periodType == null) {
            return emptyString()
        }
        val resources = context.resources
        val res = when (periodType) {
            PeriodType.YEAR -> R.plurals.format_year_count
            PeriodType.QUARTER -> R.plurals.format_quarter_count
            PeriodType.MONTH -> R.plurals.format_month_count
        }
        return resources.getQuantityString(res, value, value)
    }

    @JvmStatic
    fun formatPeriodTypeToTermPluralsString(context: Context, value: Int?): String {
        if (value == null) {
            return emptyString()
        }
        val resources = context.resources
        return resources.getQuantityString(R.plurals.format_month_count, value, value)
    }

    @JvmStatic
    fun mapRateTypeToString(context: Context, rateType: RateType?): String {
        val resources = when (rateType) {
            RateType.ANNUITY -> R.string.annuity
            RateType.DIFFERENTIATED -> R.string.differential
            else -> R.string.empty
        }
        return context.getString(resources)
    }

    @JvmStatic
    fun mapPeriodValueToString(context: Context, period: PeriodValueEntity?): String {
        if (period == null || period.value <= 0) return emptyString()
        val res = when (period.period) {
            PeriodType.MONTH -> R.plurals.format_month_count
            PeriodType.QUARTER -> R.plurals.format_quarter_count
            PeriodType.YEAR -> R.plurals.format_year_count
        }
        return context.resources.getQuantityString(res, period.value, period.value)
    }

    @JvmStatic
    fun mapPeriodValueToEveryString(context: Context, period: PeriodValueEntity?): String {
        if (period == null) return emptyString()
        if (period.value <= 0) return context.getString(R.string.once)
        val plurals = when (period.period) {
            PeriodType.MONTH -> R.plurals.format_every_month
            PeriodType.QUARTER -> R.plurals.format_every_quarter
            PeriodType.YEAR -> R.plurals.format_every_year
        }
        return context.resources.getQuantityString(plurals, period.value, period.value)
    }

    @JvmStatic
    fun mapPretermTypeToString(context: Context, pretermType: PretermType?): String {
        val resources = when (pretermType) {
            PretermType.EARLY_DECREASE_PAYMENT -> R.string.credit_early_payment_decrease_payment
            PretermType.EARLY_DECREASE_TERM -> R.string.credit_early_payment_decrease_term
            else -> R.string.empty
        }
        return context.getString(resources)
    }


}