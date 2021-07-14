package ru.rager.credit.presentation.util.binding.converters.oneway

import android.content.Context
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.util.emptyString

object CreditPeriodTypeMapper {

    @JvmStatic
    fun toRatePeriod(creditPeriod: CreditPeriodType?): Int {
        return when (creditPeriod) {
            CreditPeriodType.EVERY_YEAR -> R.string.credit_rate_every_year
            CreditPeriodType.EVERY_QUARTER -> R.string.credit_rate_every_quarter
            CreditPeriodType.EVERY_MONTH -> R.string.credit_rate_every_month
            else -> R.string.empty
        }
    }

    @JvmStatic
    fun toRatePeriod(creditPeriodList: List<CreditPeriodType>?): List<Int> {
        return creditPeriodList.orEmpty().map(this::toRatePeriod)
    }

    @JvmStatic
    fun toPaymentPeriod(creditPeriod: CreditPeriodType?): Int {
        return when (creditPeriod) {
            CreditPeriodType.EVERY_YEAR -> R.string.credit_payment_every_year
            CreditPeriodType.EVERY_QUARTER -> R.string.credit_payment_every_quarter
            CreditPeriodType.EVERY_MONTH -> R.string.credit_payment_every_month
            else -> R.string.empty
        }
    }

    @JvmStatic
    fun toPaymentPeriod(creditPeriodList: List<CreditPeriodType>?): List<Int> {
        return creditPeriodList.orEmpty().map(this::toPaymentPeriod)
    }

    @JvmStatic
    fun toTermNumber(context: Context, creditTerm: Int?, creditPaymentPeriodType: CreditPeriodType?): String {
        if (creditTerm == null || creditPaymentPeriodType == null) {
            return emptyString()
        }
        val resources = context.resources
        val res = when (creditPaymentPeriodType) {
            CreditPeriodType.EVERY_YEAR -> R.plurals.format_year
            CreditPeriodType.EVERY_QUARTER -> R.plurals.format_quarter
            CreditPeriodType.EVERY_MONTH -> R.plurals.format_month
        }
        return resources.getQuantityString(res, creditTerm, creditTerm)
    }

}