package ru.rager.credit.presentation.util.binding.converters

import android.content.Context
import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.presentation.R

object BindingConverters {

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
    fun creditFrequencyTypeToRateFrequency(creditFrequency: CreditFrequencyType?): Int {
        return when (creditFrequency) {
            CreditFrequencyType.EVERY_YEAR -> R.string.credit_rate_every_year
            CreditFrequencyType.EVERY_QUARTER -> R.string.credit_rate_every_quarter
            CreditFrequencyType.EVERY_MONTH -> R.string.credit_rate_every_month
            else -> R.string.empty
        }
    }

    @JvmStatic
    fun creditFrequencyListToRateFrequencyStringList(creditFrequencyList: List<CreditFrequencyType>): List<Int> {
        return creditFrequencyList.map(this::creditFrequencyTypeToRateFrequency)
    }

    @JvmStatic
    fun creditFrequencyTypeToPaymentFrequency(creditFrequency: CreditFrequencyType?): Int {
        return when (creditFrequency) {
            CreditFrequencyType.EVERY_YEAR -> R.string.credit_payment_every_year
            CreditFrequencyType.EVERY_QUARTER -> R.string.credit_payment_every_quarter
            CreditFrequencyType.EVERY_MONTH -> R.string.credit_payment_every_month
            else -> R.string.empty
        }
    }

    @JvmStatic
    fun creditFrequencyListToPaymentFrequencyStringList(creditFrequencyList: List<CreditFrequencyType>): List<Int> {
        return creditFrequencyList.map(this::creditFrequencyTypeToPaymentFrequency)
    }

    @JvmStatic
    fun creditTermToString(context: Context, creditTerm: Int, creditPaymentFrequencyType: CreditFrequencyType): String {
        val resources = context.resources
        val res = when (creditPaymentFrequencyType) {
            CreditFrequencyType.EVERY_YEAR -> R.plurals.format_year
            CreditFrequencyType.EVERY_QUARTER -> R.plurals.format_quarter
            CreditFrequencyType.EVERY_MONTH -> R.plurals.format_month
        }
        return resources.getQuantityString(res, creditTerm, creditTerm)
    }

    @JvmStatic
    fun creditTermIndexToString(context: Context, index: Int, creditPaymentFrequencyType: CreditFrequencyType): String {
        val resources = context.resources
        val res = when (creditPaymentFrequencyType) {
            CreditFrequencyType.EVERY_YEAR -> R.string.format_number_year
            CreditFrequencyType.EVERY_QUARTER -> R.string.format_number_quarter
            CreditFrequencyType.EVERY_MONTH -> R.string.format_number_month
        }
        return resources.getString(res, index + 1)
    }

}