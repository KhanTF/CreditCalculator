package ru.rager.credit.domain.calculator.impl

import ru.rager.credit.domain.calculator.CreditCalculator
import ru.rager.credit.domain.calculator.CreditCalculator.Companion.PERCENT_CALCULATION_START_ACCURACY
import ru.rager.credit.domain.calculator.CreditCalculator.Companion.PERCENT_CALCULATION_START_RATE
import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.exceptions.CalculationException
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.pow

class AnnuityCreditCalculator @Inject constructor() : CreditCalculator {

    override fun getPayment(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        paymentIndex: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Double = when {
        creditRate <= 0 -> creditSum / creditTerm
        else -> {
            val p = getP(creditRate, creditRateFrequency, creditPaymentFrequency)
            creditSum * (p + p / ((1 + p).pow(creditTerm) - 1))
        }
    }

    override fun getPercentPartOfPayment(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        paymentIndex: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Double {
        if (paymentIndex > creditTerm) throw CalculationException("Payment index more than credit term")
        val p = getP(creditRate, creditRateFrequency, creditPaymentFrequency)
        val remainingDebt = getDebtRemainingBeforePayment(creditSum, creditRate, creditTerm, paymentIndex, creditRateFrequency, creditPaymentFrequency)
        return remainingDebt * p
    }

    override fun getDebtPartOfPayment(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        paymentIndex: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Double {
        if (paymentIndex > creditTerm) throw CalculationException("Payment index more than credit term")
        val p = getP(creditRate, creditRateFrequency, creditPaymentFrequency)
        val payment = getPayment(creditSum, creditRate, creditTerm, paymentIndex, creditRateFrequency, creditPaymentFrequency)
        val remainingDebt = getDebtRemainingBeforePayment(creditSum, creditRate, creditTerm, paymentIndex, creditRateFrequency, creditPaymentFrequency)
        return max(payment - remainingDebt * p, 0.0)
    }

    override fun getDebtRemainingBeforePayment(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        paymentIndex: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Double {
        if (paymentIndex > creditTerm) throw CalculationException("Payment index more than credit term")
        val p = getP(creditRate, creditRateFrequency, creditPaymentFrequency)
        val payment = getPayment(creditSum, creditRate, creditTerm, paymentIndex, creditRateFrequency, creditPaymentFrequency)
        var remainingDebt = creditSum
        for (monthIndex in 0 until paymentIndex) {
            remainingDebt -= payment - remainingDebt * p
        }
        return remainingDebt
    }

    override fun getDebtRemainingAfterPayment(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        paymentIndex: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Double {
        if (paymentIndex > creditTerm) throw CalculationException("Payment index more than credit term")
        val p = getP(creditRate, creditRateFrequency, creditPaymentFrequency)
        val payment = getPayment(creditSum, creditRate, creditTerm, paymentIndex, creditRateFrequency, creditPaymentFrequency)
        var remainingDebt = creditSum
        for (monthIndex in 0..paymentIndex) {
            remainingDebt -= payment - remainingDebt * p
        }
        return max(remainingDebt, 0.0)
    }

    override fun getRate(sum: Double, payment: Double, term: Int, creditRateFrequency: CreditFrequencyType, creditPaymentFrequency: CreditFrequencyType): Double {
        return getRecursiveRate(sum, payment, term, creditRateFrequency, creditPaymentFrequency, PERCENT_CALCULATION_START_RATE, PERCENT_CALCULATION_START_ACCURACY)
    }

}