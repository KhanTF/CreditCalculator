package ru.rager.credit.domain.calculator.impl

import ru.rager.credit.domain.calculator.CreditCalculator
import ru.rager.credit.domain.calculator.CreditCalculator.Companion.PERCENT_CALCULATION_START_ACCURACY
import ru.rager.credit.domain.calculator.CreditCalculator.Companion.PERCENT_CALCULATION_START_RATE
import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.exceptions.CalculationException
import javax.inject.Inject
import kotlin.math.max

class DifferentiatedCreditCalculator @Inject constructor() : CreditCalculator {

    override fun getPayment(creditSum: Double, creditRate: Double, creditTerm: Int, paymentIndex: Int, creditRateFrequency: CreditFrequencyType, creditPaymentFrequency: CreditFrequencyType): Double {
        if (paymentIndex > creditTerm) throw CalculationException("Payment index more than credit term")
        return getPercentPartOfPayment(
            creditSum,
            creditRate,
            creditTerm,
            paymentIndex,
            creditRateFrequency,
            creditPaymentFrequency
        ) + getDebtPartOfPayment(
            creditSum,
            creditRate,
            creditTerm,
            paymentIndex,
            creditRateFrequency,
            creditPaymentFrequency
        )
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
    ): Double = creditSum / creditTerm

    override fun getDebtRemainingBeforePayment(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        paymentIndex: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ): Double {
        if (paymentIndex > creditTerm) throw CalculationException("Payment index more than credit term")
        val debtPart = getDebtPartOfPayment(creditSum, creditRate, creditTerm, paymentIndex, creditRateFrequency, creditPaymentFrequency)
        return creditSum - debtPart * paymentIndex
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
        val debtPart = getDebtPartOfPayment(creditSum, creditRate, creditTerm, paymentIndex, creditRateFrequency, creditPaymentFrequency)
        return max(creditSum - debtPart * (paymentIndex + 1), 0.0)
    }

    override fun getRate(sum: Double, payment: Double, term: Int, creditRateFrequency: CreditFrequencyType, creditPaymentFrequency: CreditFrequencyType): Double {
        return getRecursiveRate(sum, payment, term, creditRateFrequency, creditPaymentFrequency, PERCENT_CALCULATION_START_RATE, PERCENT_CALCULATION_START_ACCURACY)
    }

}