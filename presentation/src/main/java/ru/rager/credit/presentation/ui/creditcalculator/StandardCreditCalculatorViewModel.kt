package ru.rager.credit.presentation.ui.creditcalculator

import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.domain.usecase.TemplateInteractor
import ru.rager.credit.presentation.dto.CreditParametersParcelable
import javax.inject.Inject

class StandardCreditCalculatorViewModel @Inject constructor(
    templateInteractor: TemplateInteractor,
    creditCalculatorFragmentArgs: CreditCalculatorFragmentArgs
) : CreditCalculatorViewModel(templateInteractor, creditCalculatorFragmentArgs) {

    init {
        isCreditPaymentVisible.value = false
    }

    override fun onCalculate() {
        val creditStart = creditStart.value ?: return
        val creditRateType = rateType.value ?: return
        val creditRatePeriodType = ratePeriod.value ?: return
        val creditPaymentPeriod = paymentPeriod.value ?: return
        val creditSum = creditSum.value?.toDoubleOrNull() ?: return
        val creditRate = creditRate.value?.toDoubleOrNull() ?: return
        val creditTerm = creditTerm.value?.toIntOrNull() ?: return
        val parameters = CreditParametersEntity.Builder()
            .setCreditStart(creditStart)
            .setCreditSum(creditSum)
            .setCreditRate(creditRate)
            .setCreditRateType(creditRateType)
            .setCreditTerm(creditTerm)
            .setCreditPaymentPeriod(creditPaymentPeriod)
            .setCreditRatePeriod(creditRatePeriodType)
            .build()
        postNavigationEvent(CreditCalculatorFragmentDirections.toCalculationFragment(CreditParametersParcelable(parameters)))
    }

    override fun isCalculationAvailable(): Boolean {
        return isCreditSumValid(creditSum.value ?: return false)
                && isCreditRateValid(creditRate.value ?: return false)
                && isCreditTermValid(creditTerm.value ?: return false)
                && rateType.value != null
                && ratePeriod.value != null
                && paymentPeriod.value != null
    }
}