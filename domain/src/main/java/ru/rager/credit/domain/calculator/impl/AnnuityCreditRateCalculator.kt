package ru.rager.credit.domain.calculator.impl

import ru.rager.credit.domain.calculator.CreditRateCalculator
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import javax.inject.Inject
import kotlin.math.pow

class AnnuityCreditRateCalculator @Inject constructor() : CreditRateCalculator() {

    override fun getPayment(creditSum: Double, creditRate: Double, creditTerm: Int, creditRatePeriod: CreditPeriodType, creditPaymentPeriod: CreditPeriodType): Double {
        val p = getPForPeriod(creditRate, creditRatePeriod, creditPaymentPeriod)
        return creditSum * (p + p / ((1 + p).pow(creditTerm) - 1))
    }

}