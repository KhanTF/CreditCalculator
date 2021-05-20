package ru.rager.credit.domain.usecase

import io.reactivex.Single
import ru.rager.credit.domain.calculator.CreditCalculator
import ru.rager.credit.domain.di.qualifiers.AnnuityQualifier
import ru.rager.credit.domain.di.qualifiers.DifferentiatedQualifier
import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.entity.enums.CreditRateType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCreditRateUseCase @Inject constructor(
    @AnnuityQualifier
    private val annuityCreditCalculator: CreditCalculator,
    @DifferentiatedQualifier
    private val differentiatedCalculator: CreditCalculator
) {

    fun getCreditRateSingle(
        creditRateType: CreditRateType,
        creditSum: Double,
        creditFirstPayment: Double,
        creditTerm: Int,
        creditRateFrequency: CreditFrequencyType = CreditFrequencyType.EVERY_YEAR,
        creditPaymentFrequency: CreditFrequencyType = CreditFrequencyType.EVERY_MONTH
    ) = Single.fromCallable {
        when (creditRateType) {
            CreditRateType.ANNUITY -> annuityCreditCalculator.getRate(creditSum, creditFirstPayment, creditTerm, creditRateFrequency, creditPaymentFrequency)
            CreditRateType.DIFFERENTIATED -> differentiatedCalculator.getRate(creditSum, creditFirstPayment, creditTerm, creditRateFrequency, creditPaymentFrequency)
        }
    }

}