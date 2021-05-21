package ru.rager.credit.domain.usecase

import io.reactivex.Single
import ru.rager.credit.domain.calculator.CreditCalculator
import ru.rager.credit.domain.di.qualifiers.AnnuityQualifier
import ru.rager.credit.domain.di.qualifiers.DifferentiatedQualifier
import ru.rager.credit.domain.entity.CalculationPaymentEntity
import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.utils.sequenceUntil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPaymentListUseCase @Inject constructor(
    @AnnuityQualifier
    private val annuityCreditCalculator: CreditCalculator,
    @DifferentiatedQualifier
    private val differentiatedCalculator: CreditCalculator
) {

    fun getPaymentListSingle(
        creditRateType: CreditRateType,
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType
    ) = Single.fromCallable {
        when (creditRateType) {
            CreditRateType.ANNUITY -> getPaymentsList(creditSum, creditRate, creditTerm, creditRateFrequency, creditPaymentFrequency, annuityCreditCalculator)
            CreditRateType.DIFFERENTIATED -> getPaymentsList(creditSum, creditRate, creditTerm, creditRateFrequency, creditPaymentFrequency, differentiatedCalculator)
        }
    }

    private fun getPaymentsList(
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRateFrequency: CreditFrequencyType,
        creditPaymentFrequency: CreditFrequencyType,
        creditCalculator: CreditCalculator
    ) = sequenceUntil(0, creditTerm).map { paymentIndex ->
        val payment = creditCalculator.getPayment(creditSum, creditRate, creditTerm, paymentIndex, creditRateFrequency, creditPaymentFrequency)
        val percentPart = creditCalculator.getPercentPartOfPayment(creditSum, creditRate, creditTerm, paymentIndex, creditRateFrequency, creditPaymentFrequency)
        val debtPart = creditCalculator.getDebtPartOfPayment(creditSum, creditRate, creditTerm, paymentIndex, creditRateFrequency, creditPaymentFrequency)
        val remainingDebt = creditCalculator.getDebtRemainingAfterPayment(creditSum, creditRate, creditTerm, paymentIndex, creditRateFrequency, creditPaymentFrequency)
        CalculationPaymentEntity(
            creditPaymentFrequency = creditPaymentFrequency,
            creditPaymentOrder = paymentIndex,
            creditPayment = payment,
            creditPercentPartOfPayment = percentPart,
            creditDebtPartOfPayment = debtPart,
            creditDebtReminder = remainingDebt
        )
    }

}