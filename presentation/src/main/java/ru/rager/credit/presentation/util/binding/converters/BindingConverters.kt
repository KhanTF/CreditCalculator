package ru.rager.credit.presentation.util.binding.converters

import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.presentation.R

object BindingConverters {

    @JvmStatic
    fun creditCalculationPercentTypeToIntString(creditRateType: CreditRateType?): Int {
        return when (creditRateType) {
            CreditRateType.ANNUITY -> R.string.annuity
            CreditRateType.DIFFERENTIATED -> R.string.differential
            else -> R.string.empty
        }
    }

    @JvmStatic
    fun creditCalculationPercentTypeToPaymentHint(creditRateType: CreditRateType?): Int {
        return when (creditRateType) {
            CreditRateType.ANNUITY -> R.string.credit_month_payment
            CreditRateType.DIFFERENTIATED -> R.string.credit_first_month_payment
            else -> R.string.empty
        }
    }

    @JvmStatic
    fun creditCalculationPercentTypeListToIntStringList(creditRateTypeList: List<CreditRateType>): List<Int> {
        return creditRateTypeList.map(this::creditCalculationPercentTypeToIntString)
    }

}