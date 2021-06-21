package ru.rager.credit.domain.usecase

import io.reactivex.Single
import ru.rager.credit.domain.calculator.CreditPaymentCalculator
import ru.rager.credit.domain.calculator.CreditRateCalculator
import ru.rager.credit.domain.di.qualifiers.AnnuityQualifier
import ru.rager.credit.domain.di.qualifiers.DifferentiatedQualifier
import ru.rager.credit.domain.entity.CreditEarlyPaymentEntity
import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.domain.entity.CreditRateChangesEntity
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.CreditRateType
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
        parameters.creditEarlyPaymentEntityList,
        parameters.creditRateChangesList
    )

    fun getCalculationListSingle(
        creditStart: Calendar,
        creditSum: Double,
        creditRate: Double,
        creditRateType: CreditRateType,
        creditTerm: Int,
        creditSkipWeekend: Boolean,
        creditRatePeriod: CreditPeriodType,
        creditPaymentPeriod: CreditPeriodType,
        creditEarlyPaymentEntityList: List<CreditEarlyPaymentEntity>,
        creditRateChangesList: List<CreditRateChangesEntity>
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
            creditEarlyPaymentEntityList = creditEarlyPaymentEntityList,
            creditRateChangesList = creditRateChangesList
        )
    }

    fun getCreditRateSingle(
        creditSum: Double,
        creditPaymentSum: Double,
        creditRateType: CreditRateType,
        creditTerm: Int,
        creditRatePeriod: CreditPeriodType,
        creditPaymentPeriod: CreditPeriodType
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
        creditRateType: CreditRateType,
        creditTerm: Int,
        creditSkipWeekend: Boolean,
        creditRatePeriod: CreditPeriodType,
        creditPaymentPeriod: CreditPeriodType,
        creditEarlyPaymentEntityList: List<CreditEarlyPaymentEntity>,
        creditRateChangesList: List<CreditRateChangesEntity>
    ) = when (creditRateType) {
        CreditRateType.ANNUITY -> annuityCreditPaymentCalculator.calculate(
            creditStart = creditStart,
            creditSum = creditSum,
            creditRate = creditRate,
            creditTerm = creditTerm,
            isSkipWeekend = creditSkipWeekend,
            creditRatePeriod = creditRatePeriod,
            creditPaymentPeriod = creditPaymentPeriod,
            creditEarlyPaymentList = creditEarlyPaymentEntityList,
            creditRateChangesList = creditRateChangesList
        )
        CreditRateType.DIFFERENTIATED -> differentiatedCreditPaymentCalculator.calculate(
            creditStart = creditStart,
            creditSum = creditSum,
            creditRate = creditRate,
            creditTerm = creditTerm,
            isSkipWeekend = creditSkipWeekend,
            creditRatePeriod = creditRatePeriod,
            creditPaymentPeriod = creditPaymentPeriod,
            creditEarlyPaymentList = creditEarlyPaymentEntityList,
            creditRateChangesList = creditRateChangesList
        )
    }

    private fun getCreditRate(
        creditSum: Double,
        creditPaymentSum: Double,
        creditRateType: CreditRateType,
        creditTerm: Int,
        creditRatePeriod: CreditPeriodType,
        creditPaymentPeriod: CreditPeriodType
    ) = when (creditRateType) {
        CreditRateType.ANNUITY -> annuityCreditRateCalculator.calculate(
            creditSum = creditSum,
            creditPaymentSum = creditPaymentSum,
            creditTerm = creditTerm,
            creditRatePeriod = creditRatePeriod,
            creditPaymentPeriod = creditPaymentPeriod
        )
        CreditRateType.DIFFERENTIATED -> differentiatedCreditRateCalculator.calculate(
            creditSum = creditSum,
            creditPaymentSum = creditPaymentSum,
            creditTerm = creditTerm,
            creditRatePeriod = creditRatePeriod,
            creditPaymentPeriod = creditPaymentPeriod
        )
    }

}