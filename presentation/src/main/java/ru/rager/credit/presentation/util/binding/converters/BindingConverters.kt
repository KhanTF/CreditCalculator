package ru.rager.credit.presentation.util.binding.converters

import ru.rager.credit.domain.entity.enums.CreditCalculationType
import ru.rager.credit.presentation.R

object BindingConverters {

    @JvmStatic
    fun creditCalculationPercentTypeToIntString(creditCalculationType: CreditCalculationType): Int {
        return when (creditCalculationType) {
            CreditCalculationType.ANNUITY -> R.string.annuity
            CreditCalculationType.DIFFERENTIATED -> R.string.differential
        }
    }

    @JvmStatic
    fun creditCalculationPercentTypeListToIntStringList(creditCalculationTypeList: List<CreditCalculationType>): List<Int> {
        return creditCalculationTypeList.map(this::creditCalculationPercentTypeToIntString)
    }

}