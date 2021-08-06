package ru.rager.credit.domain.calculator.impl

import ru.rager.credit.domain.calculator.CreditRateCalculator
import ru.rager.credit.domain.entity.enums.PeriodType
import javax.inject.Inject

class DifferentiatedCreditRateCalculator @Inject constructor() : CreditRateCalculator() {

    override fun getPayment(creditSum: Double, creditRate: Double, creditTerm: Int, creditRatePeriod: PeriodType, creditPaymentPeriod: PeriodType): Double {
        val p = getPForPeriod(creditRate, creditRatePeriod, creditPaymentPeriod)
        return creditSum / creditTerm + creditSum * p
    }

}