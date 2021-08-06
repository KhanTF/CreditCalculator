package ru.rager.credit.domain.usecase

import io.reactivex.Single
import ru.rager.credit.domain.calculator.CreditPaymentCalculator
import ru.rager.credit.domain.calculator.CreditRateCalculator
import ru.rager.credit.domain.di.qualifiers.AnnuityQualifier
import ru.rager.credit.domain.di.qualifiers.DifferentiatedQualifier
import ru.rager.credit.domain.entity.CreditPretermPaymentEntity
import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.domain.entity.CreditRateChangeEntity
import ru.rager.credit.domain.entity.enums.PeriodType
import ru.rager.credit.domain.entity.enums.RateType
import java.util.*
import javax.inject.Inject

class CreditCalculatorInteractor @Inject constructor(
    @AnnuityQualifier
    private val annuityCreditPaymentCalculator: CreditPaymentCalculator,
    @DifferentiatedQualifier
    private val differentiatedCreditPaymentCalculator: CreditPaymentCalculator,
    @AnnuityQualifier
    private val annuityCreditRateCalculator: CreditRateCalculator,
    @DifferentiatedQualifier
    private val differentiatedCreditRateCalculator: CreditRateCalculator
) {

    fun getCalculationListSingle(
        parameters: CreditParametersEntity
    ) = getCalculationListSingle(
        parameters.creditStart,
        parameters.creditSum,
        parameters.creditRate,
        parameters.creditRateType,
        parameters.creditTerm,
        parameters.creditSkipWeekend,
        parameters.creditRatePeriod,
        parameters.creditPaymentPeriod,
        parameters.creditPretermPaymentEntityList,
        parameters.creditRateChangeList
    )

    fun getCalculationListSingle(
        creditStart: Calendar,
        creditSum: Double,
        creditRate: Double,
        creditRateType: RateType,
        creditTerm: Int,
        creditSkipWeekend: Boolean,
        creditRatePeriod: PeriodType,
        creditPaymentPeriod: PeriodType,
        creditPretermPaymentEntityList: List<CreditPretermPaymentEntity>,
        creditRateChangeList: List<CreditRateChangeEntity>
    ) = Single.fromCallable {
        getCalculationList(
            creditStart = creditStart,
            creditSum = creditSum,
            creditRate = creditRate,
            creditRateType = creditRateType,
            creditTerm = creditTerm,
            creditSkipWeekend = creditSkipWeekend,
            creditRatePeriod = creditRatePeriod,
            creditPaymentPeriod = creditPaymentPeriod,
            creditPretermPaymentEntityList = creditPretermPaymentEntityList,
            creditRateChangeList = creditRateChangeList
        )
    }

    fun getCreditRateSingle(
        creditSum: Double,
        creditPaymentSum: Double,
        creditRateType: RateType,
        creditTerm: Int,
        creditRatePeriod: PeriodType,
        creditPaymentPeriod: PeriodType
    ) = Single.fromCallable {
        getCreditRate(
            creditSum = creditSum,
            creditPaymentSum = creditPaymentSum,
            creditRateType = creditRateType,
            creditTerm = creditTerm,
            creditRatePeriod = creditRatePeriod,
            creditPaymentPeriod = creditPaymentPeriod
        )
    }

    private fun getCalculationList(
        creditStart: Calendar,
        creditSum: Double,
        creditRate: Double,
        creditRateType: RateType,
        creditTerm: Int,
        creditSkipWeekend: Boolean,
        creditRatePeriod: PeriodType,
        creditPaymentPeriod: PeriodType,
        creditPretermPaymentEntityList: List<CreditPretermPaymentEntity>,
        creditRateChangeList: List<CreditRateChangeEntity>
    ) = when (creditRateType) {
        RateType.ANNUITY -> annuityCreditPaymentCalculator.calculate(
            creditStart = creditStart,
            creditSum = creditSum,
            creditRate = creditRate,
            creditTerm = creditTerm,
            isSkipWeekend = creditSkipWeekend,
            creditRatePeriod = creditRatePeriod,
            creditPaymentPeriod = creditPaymentPeriod,
            creditPretermPaymentList = creditPretermPaymentEntityList,
            creditRateChangeList = creditRateChangeList
        )
        RateType.DIFFERENTIATED -> differentiatedCreditPaymentCalculator.calculate(
            creditStart = creditStart,
            creditSum = creditSum,
            creditRate = creditRate,
            creditTerm = creditTerm,
            isSkipWeekend = creditSkipWeekend,
            creditRatePeriod = creditRatePeriod,
            creditPaymentPeriod = creditPaymentPeriod,
            creditPretermPaymentList = creditPretermPaymentEntityList,
            creditRateChangeList = creditRateChangeList
        )
    }

    private fun getCreditRate(
        creditSum: Double,
        creditPaymentSum: Double,
        creditRateType: RateType,
        creditTerm: Int,
        creditRatePeriod: PeriodType,
        creditPaymentPeriod: PeriodType
    ) = when (creditRateType) {
        RateType.ANNUITY -> annuityCreditRateCalculator.calculate(
            creditSum = creditSum,
            creditPaymentSum = creditPaymentSum,
            creditTerm = creditTerm,
            creditRatePeriod = creditRatePeriod,
            creditPaymentPeriod = creditPaymentPeriod
        )
        RateType.DIFFERENTIATED -> differentiatedCreditRateCalculator.calculate(
            creditSum = creditSum,
            creditPaymentSum = creditPaymentSum,
            creditTerm = creditTerm,
            creditRatePeriod = creditRatePeriod,
            creditPaymentPeriod = creditPaymentPeriod
        )
    }

}