package ru.rager.credit.presentation.util.binding.converters

import android.content.Context
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.presentation.R
import java.text.SimpleDateFormat
import java.util.*

object BindingConverters {

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    @JvmStatic
    fun creditRateTypeToIntString(creditRateType: CreditRateType?): Int {
        return when (creditRateType) {
            CreditRateType.ANNUITY -> R.string.annuity
            CreditRateType.DIFFERENTIATED -> R.string.differential
            else -> R.string.empty
        }
    }

    @JvmStatic
    fun creditRateTypeToPaymentHint(creditRateType: CreditRateType?): Int {
        return when (creditRateType) {
            CreditRateType.ANNUITY -> R.string.credit_month_payment
            CreditRateType.DIFFERENTIATED -> R.string.credit_first_month_payment
            else -> R.string.empty
        }
    }

    @JvmStatic
    fun creditRateTypeListToIntStringList(creditRateTypeList: List<CreditRateType>): List<Int> {
        return creditRateTypeList.map(this::creditRateTypeToIntString)
    }

    @JvmStatic
    fun creditFrequencyTypeToRateFrequency(creditPeriod: CreditPeriodType?): Int {
        return when (creditPeriod) {
            CreditPeriodType.EVERY_YEAR -> R.string.credit_rate_every_year
            CreditPeriodType.EVERY_QUARTER -> R.string.credit_rate_every_quarter
            CreditPeriodType.EVERY_MONTH -> R.string.credit_rate_every_month
            else -> R.string.empty
        }
    }

    @JvmStatic
    fun creditFrequencyListToRateFrequencyStringList(creditPeriodList: List<CreditPeriodType>): List<Int> {
        return creditPeriodList.map(this::creditFrequencyTypeToRateFrequency)
    }

    @JvmStatic
    fun creditFrequencyTypeToPaymentFrequency(creditPeriod: CreditPeriodType?): Int {
        return when (creditPeriod) {
            CreditPeriodType.EVERY_YEAR -> R.string.credit_payment_every_year
            CreditPeriodType.EVERY_QUARTER -> R.string.credit_payment_every_quarter
            CreditPeriodType.EVERY_MONTH -> R.string.credit_payment_every_month
            else -> R.string.empty
        }
    }

    @JvmStatic
    fun creditFrequencyListToPaymentFrequencyStringList(creditPeriodList: List<CreditPeriodType>): List<Int> {
        return creditPeriodList.map(this::creditFrequencyTypeToPaymentFrequency)
    }

    @JvmStatic
    fun creditTermToString(context: Context, creditTerm: Int, creditPaymentPeriodType: CreditPeriodType): String {
        val resources = context.resources
        val res = when (creditPaymentPeriodType) {
            CreditPeriodType.EVERY_YEAR -> R.plurals.format_year
            CreditPeriodType.EVERY_QUARTER -> R.plurals.format_quarter
            CreditPeriodType.EVERY_MONTH -> R.plurals.format_month
        }
        return resources.getQuantityString(res, creditTerm, creditTerm)
    }

    @JvmStatic
    fun creditTermIndexToString(context: Context, index: Int, creditPaymentPeriodType: CreditPeriodType): String {
        val resources = context.resources
        val res = when (creditPaymentPeriodType) {
            CreditPeriodType.EVERY_YEAR -> R.string.format_number_year
            CreditPeriodType.EVERY_QUARTER -> R.string.format_number_quarter
            CreditPeriodType.EVERY_MONTH -> R.string.format_number_month
        }
        return resources.getString(res, index + 1)
    }

    @JvmStatic
    fun dateToString(calendar: Calendar): String {
        return dateFormatter.format(calendar.time)
    }

}