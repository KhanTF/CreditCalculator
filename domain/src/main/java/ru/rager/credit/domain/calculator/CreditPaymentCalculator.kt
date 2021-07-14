package ru.rager.credit.domain.calculator

import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditEarlyPaymentEntity
import ru.rager.credit.domain.entity.CreditRateChangesEntity
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.EarlyPaymentType
import ru.rager.credit.domain.utils.equalsByDate
import ru.rager.credit.domain.utils.getDaysCount
import ru.rager.credit.domain.utils.nextDayOfMonth
import ru.rager.credit.domain.utils.skipWeekend
import java.util.*
import kotlin.math.min

abstract class CreditPaymentCalculator {

    fun calculate(
        creditStart: Calendar,
        creditSum: Double,
        creditRate: Double,
        creditTerm: Int,
        isSkipWeekend: Boolean,
        creditRatePeriod: CreditPeriodType,
        creditPaymentPeriod: CreditPeriodType,
        creditEarlyPaymentList: List<CreditEarlyPaymentEntity>,
        creditRateChangesList: List<CreditRateChangesEntity>
    ): List<CreditCalculationEntity> {
        val all = mutableListOf<CreditCalculationEntity>()
        var step = 0
        val currentCalculationStart = creditStart.clone() as Calendar
        var currentCalculationDate = creditStart.clone() as Calendar
        var currentCalculationPeriodIndex = 0
        var currentCreditPeriodIndex = 0
        var currentCreditSum = creditSum
        var currentCreditRate = creditRate
        val currentIsSkipWeekend = isSkipWeekend
        val currentCreditRatePeriod = creditRatePeriod
        val currentCreditPaymentPeriod = creditPaymentPeriod
        var currentCreditTerm = creditTerm
        var currentTotalRemainingDebtSum = currentCreditSum
        var currentTotalAccruedPercentSum = 0.0
        var currentCreditMinPaymentSum = 0.0

        while (currentTotalAccruedPercentSum > 0 || currentTotalRemainingDebtSum > 0) {
            if (step > 4) {
                step = 0
            } else {
                step++
            }
            when (step) {
                0 -> {
                    currentCalculationDate = currentCalculationDate.nextDayOfMonth()
                }
                1 -> {
                    val p = getPForDate(currentCalculationDate, creditRate, creditRatePeriod)
                    currentTotalAccruedPercentSum += currentTotalRemainingDebtSum * p
                }
                2 -> {
                    val nextPaymentDate = getPaymentDate(
                        calculationStart = currentCalculationStart,
                        creditPaymentPeriod = currentCreditPaymentPeriod,
                        calculationPeriodIndex = currentCalculationPeriodIndex,
                        isSkipWeekend = currentIsSkipWeekend
                    )
                    if (nextPaymentDate.equalsByDate(currentCalculationDate)) {
                        val paymentSum = getPayment(
                            creditSum = currentCreditSum,
                            creditMinPaymentSum = currentCreditMinPaymentSum,
                            creditRate = currentCreditRate,
                            creditTerm = currentCreditTerm,
                            creditRatePeriod = currentCreditRatePeriod,
                            creditPaymentPeriod = currentCreditPaymentPeriod,
                            currentTotalRemainingDebtSum = currentTotalRemainingDebtSum,
                            currentTotalAccruedPercentSum = currentTotalAccruedPercentSum
                        )
                        val paidPercentSum = when {
                            currentCreditPeriodIndex >= currentCreditTerm - 1 -> currentTotalAccruedPercentSum
                            else -> min(currentTotalAccruedPercentSum, paymentSum)
                        }
                        val paidDebtSum = when {
                            currentCreditPeriodIndex >= currentCreditTerm - 1 -> currentTotalRemainingDebtSum
                            else -> min(paymentSum - paidPercentSum, currentTotalRemainingDebtSum)
                        }
                        currentTotalRemainingDebtSum -= paidDebtSum
                        currentTotalAccruedPercentSum -= paidPercentSum
                        currentCalculationPeriodIndex++
                        currentCreditPeriodIndex++
                        val calculation = CreditCalculationEntity.SchedulePaymentCreditCalculationEntity(
                            paidPercentSum = paidPercentSum,
                            paidDebtSum = paidDebtSum,
                            calculationDate = currentCalculationDate,
                            currentCreditRemainingDebtSum = currentTotalRemainingDebtSum,
                            currentCreditAccruedPercentSum = currentTotalAccruedPercentSum
                        )
                        all.add(calculation)
                    }
                }
                3 -> {
                    val nextEarlyPaymentList = creditEarlyPaymentList.filter { it.isEarlyPaymentDate(currentCalculationDate, currentIsSkipWeekend) }
                    var nextEarlyPaymentIndex = 0
                    while (nextEarlyPaymentIndex < nextEarlyPaymentList.size && currentTotalAccruedPercentSum > 0 && currentTotalRemainingDebtSum > 0) {
                        val nextEarlyPayment = nextEarlyPaymentList[nextEarlyPaymentIndex++]
                        val earlyPaymentType = nextEarlyPayment.earlyPaymentType
                        val earlyPaymentSum = nextEarlyPayment.earlyPaymentSum
                        val earlyPaidPercentSum = min(currentTotalAccruedPercentSum, earlyPaymentSum)
                        val earlyPaidDebtSum = min(earlyPaymentSum - earlyPaidPercentSum, currentTotalRemainingDebtSum)

                        when (earlyPaymentType) {
                            EarlyPaymentType.EARLY_DECREASE_PAYMENT -> {
                                currentTotalRemainingDebtSum -= earlyPaidDebtSum
                                currentTotalAccruedPercentSum -= earlyPaidPercentSum
                                currentCreditSum = currentTotalRemainingDebtSum
                                currentCreditTerm -= currentCreditPeriodIndex
                                currentCreditPeriodIndex = 0
                                currentCreditMinPaymentSum = 0.0
                            }
                            EarlyPaymentType.EARLY_DECREASE_TERM -> {
                                currentTotalRemainingDebtSum -= earlyPaidDebtSum
                                currentTotalAccruedPercentSum -= earlyPaidPercentSum
                                currentCreditMinPaymentSum = getPayment(
                                    creditSum = currentTotalRemainingDebtSum,
                                    creditMinPaymentSum = currentCreditMinPaymentSum,
                                    creditRate = currentCreditRate,
                                    creditTerm = currentCreditTerm,
                                    creditRatePeriod = currentCreditRatePeriod,
                                    creditPaymentPeriod = currentCreditPaymentPeriod,
                                    currentTotalRemainingDebtSum = currentTotalRemainingDebtSum,
                                    currentTotalAccruedPercentSum = 0.0
                                )
                                currentCreditTerm = getTerm(
                                    creditSum = currentTotalRemainingDebtSum,
                                    creditPaymentSum = currentCreditMinPaymentSum,
                                    creditRate = currentCreditRate,
                                    creditTerm = currentCreditTerm,
                                    creditRatePeriod = currentCreditRatePeriod,
                                    creditPaymentPeriod = currentCreditPaymentPeriod
                                )
                            }
                        }
                        val calculation = CreditCalculationEntity.EarlyPaymentCreditCalculationEntity(
                            earlyPaymentType = earlyPaymentType,
                            earlyPaidPercentSum = earlyPaidPercentSum,
                            earlyPaidDebtSum = earlyPaidDebtSum,
                            calculationDate = currentCalculationDate,
                            currentCreditRemainingDebtSum = currentTotalRemainingDebtSum,
                            currentCreditAccruedPercentSum = currentTotalAccruedPercentSum
                        )
                        all.add(calculation)
                    }
                }
                4 -> {
                    val nextCreditRateChangedList = creditRateChangesList.filter { it.isChangedRate(currentCalculationDate) }
                    for (nextCreditRateChange in nextCreditRateChangedList) {
                        currentCreditRate = nextCreditRateChange.changedRate
                        val calculation = CreditCalculationEntity.RateChangesCreditCalculationEntity(
                            changedCreditRate = currentCreditRate,
                            calculationDate = currentCalculationDate,
                            currentCreditRemainingDebtSum = currentTotalRemainingDebtSum,
                            currentCreditAccruedPercentSum = currentTotalAccruedPercentSum
                        )
                        all.add(calculation)
                    }
                }
            }
        }
        return all
    }

    protected fun getPForPeriod(
        creditRate: Double,
        creditRatePeriod: CreditPeriodType,
        creditPaymentPeriod: CreditPeriodType
    ): Double {
        return creditRate / 100 / (creditRatePeriod.value / creditPaymentPeriod.value)
    }

    protected fun getPForDate(
        creditCalculationDate: Calendar,
        creditRate: Double,
        creditRatePeriod: CreditPeriodType,
    ): Double {
        return creditRate / 100 / getDaysInRatePeriod(creditCalculationDate, creditRatePeriod)
    }

    protected abstract fun getPayment(
        creditSum: Double,
        creditMinPaymentSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRatePeriod: CreditPeriodType,
        creditPaymentPeriod: CreditPeriodType,
        currentTotalRemainingDebtSum: Double,
        currentTotalAccruedPercentSum: Double
    ): Double

    protected abstract fun getTerm(
        creditSum: Double,
        creditPaymentSum: Double,
        creditRate: Double,
        creditTerm: Int,
        creditRatePeriod: CreditPeriodType,
        creditPaymentPeriod: CreditPeriodType
    ): Int

    private fun getPaymentDate(calculationStart: Calendar, creditPaymentPeriod: CreditPeriodType, calculationPeriodIndex: Int, isSkipWeekend: Boolean): Calendar {
        val paymentDate = calculationStart.clone() as Calendar
        paymentDate.add(Calendar.MONTH, creditPaymentPeriod.value * (calculationPeriodIndex + 1))
        if (isSkipWeekend) {
            paymentDate.skipWeekend()
        }
        return paymentDate
    }

    private fun getDaysInRatePeriod(date: Calendar, creditRatePeriod: CreditPeriodType): Long {
        val dateClone = date.clone() as Calendar
        return when (creditRatePeriod) {
            CreditPeriodType.EVERY_YEAR -> {
                dateClone.set(Calendar.MONTH, 0)
                dateClone.set(Calendar.DAY_OF_MONTH, 1)
                val startTimeInMillis = dateClone.timeInMillis
                dateClone.add(Calendar.YEAR, 1)
                val endTimeInMillis = dateClone.timeInMillis
                getDaysCount(startTimeInMillis, endTimeInMillis)
            }
            CreditPeriodType.EVERY_QUARTER -> {
                val quarter = dateClone.get(Calendar.MONTH) / 4
                dateClone.set(Calendar.MONTH, quarter * 4)
                dateClone.set(Calendar.DAY_OF_MONTH, 1)
                val startTimeInMillis = dateClone.timeInMillis
                dateClone.add(Calendar.MONTH, 4)
                val endTimeInMillis = dateClone.timeInMillis
                getDaysCount(startTimeInMillis, endTimeInMillis)
            }
            CreditPeriodType.EVERY_MONTH -> {
                dateClone.set(Calendar.DAY_OF_MONTH, 1)
                val startTimeInMillis = dateClone.timeInMillis
                dateClone.add(Calendar.MONTH, 1)
                val endTimeInMillis = dateClone.timeInMillis
                getDaysCount(startTimeInMillis, endTimeInMillis)
            }
        }
    }

}