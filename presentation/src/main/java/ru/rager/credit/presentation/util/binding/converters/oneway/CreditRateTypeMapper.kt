package ru.rager.credit.presentation.util.binding.converters.oneway

import androidx.databinding.InverseMethod
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.view.MaterialSpinner

object CreditRateTypeMapper {

    @JvmStatic
    fun toRateType(creditRateType: CreditRateType?): Int {
        return when (creditRateType) {
            CreditRateType.ANNUITY -> R.string.annuity
            CreditRateType.DIFFERENTIATED -> R.string.differential
            else -> R.string.empty
        }
    }

    @JvmStatic
    fun toRateType(creditRateTypeList: List<CreditRateType>?): List<Int> {
        return creditRateTypeList.orEmpty().map(this::toRateType)
    }

    @JvmStatic
    fun toPaymentType(creditRateType: CreditRateType?): Int {
        return when (creditRateType) {
            CreditRateType.ANNUITY -> R.string.credit_month_payment
            CreditRateType.DIFFERENTIATED -> R.string.credit_first_month_payment
            else -> R.string.empty
        }
    }

}