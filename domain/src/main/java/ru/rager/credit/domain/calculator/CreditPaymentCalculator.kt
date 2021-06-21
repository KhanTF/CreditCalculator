package ru.rager.credit.domain.calculator

import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditEarlyPaymentEntity
import ru.rager.credit.domain.entity.CreditRateChangesEntity
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.EarlyPaymentType
import ru.rager.credit.domain.utils.equalsByDate
import ru.rager.credit.domain.utils.getDaysCount
import ru.rager.credit.domain.utils.nextDayOfMonth
import java.util.*
import kotlin.math.min

abstract class CreditPaymentCalculator {

    private fun getNearestPaymentCalendar(calculationPeriodIndex: Int, creditStart: Calendar, creditPaymentPeriod: CreditPeriodType, isSkipWeekend: Boolean): Calendar {
        val nearestPaymentDate = creditStart.clone() as Calendar
        nearestPaymentDate.add(Calendar.MONTH, creditPaymentPeriod.value * (calculationPeriodIndex + 1))
        if (isSkipWeekend) {
            when {
                nearestPaymentDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY -> nearestPaymentDate.add(Calendar.DAY_OF_MONTH, 2)
                nearestPaymentDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY -> nearestPaymentDate.add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        return nearestPaymentDate
    }

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
        var currentCalculationPeriodIndex = 0
        var currentCreditPeriodIndex = 0
        var currentCreditSum = creditSum
        var currentCreditRate = creditRate
        var currentCreditTerm = creditTerm
        var currentCalculationDate = creditStart.clone() as Calendar
        var currentTotalRemainingDebtSum = currentCreditSum
        var currentTotalAccruedPercentSum = 0.0
        var currentCreditMinPaymentSum = 0.0
        main@ while (currentTotalRemainingDebtSum > 0) {
            currentCalculationDate = currentCalculationDate.nextDayOfMonth()
            currentTotalAccruedPercentSum += getAccruedPercentSum(currentCalculationDate, currentCreditRate, creditRatePeriod, currentTotalRemainingDebtSum)
            val nearestPaymentCalendar = getNearestPaymentCalendar(
                calculationPeriodIndex = currentCalculationPeriodIndex,
                creditStart = creditStart,
                creditPaymentPeriod = creditPaymentPeriod,
                isSkipWeekend = isSkipWeekend
            )
            if (nearestPaymentCalendar.equalsByDate(currentCalculationDate)) {
                if (currentTotalRemainingDebtSum <= 0) break@main
                val payment = getPayment(
                    creditSum = currentCreditSum,
                    creditMinPaymentSum = currentCreditMinPaymentSum,
                    creditRate = currentCreditRate,
                    creditTerm = currentCreditTerm,
                    creditRatePeriod = creditRatePeriod,
                    creditPaymentPeriod = creditPaymentPeriod,
                    currentTotalRemainingDebtSum = currentTotalRemainingDebtSum,
                    currentTotalAccruedPercentSum = currentTotalAccruedPercentSum
                )
                val paidPercentSum = when {
                    currentCreditPeriodIndex >= currentCreditTerm - 1 -> currentTotalAccruedPercentSum
                    else -> min(currentTotalAccruedPercentSum, payment)
                }
                val paidDebtSum = when {
                    currentCreditPeriodIndex >= currentCreditTerm - 1 -> currentTotalRemainingDebtSum
                    else -> min(payment - paidPercentSum, currentTotalRemainingDebtSum)
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

            val currentEarlyPaymentList = creditEarlyPaymentList.filter { it.isEarlyPaymentDate(currentCalculationDate) }
            for (earlyPayment in currentEarlyPaymentList) {
                if (currentTotalRemainingDebtSum <= 0) break@main
                val earlyPaymentType = earlyPayment.earlyPaymentType
                val earlyPaymentSum = earlyPayment.earlyPaymentSum
                val earlyPaidPercentSum = min(currentTotalAccruedPercentSum, earlyPaymentSum)
                val earlyPaidDebtSum = min(earlyPaymentSum - earlyPaidPercentSum, currentTotalRemainingDebtSum)
                currentTotalRemainingDebtSum -= earlyPaidDebtSum
                currentTotalAccruedPercentSum -= earlyPaidPercentSum
                when (earlyPaymentType) {
                    EarlyPaymentType.EARLY_DECREASE_PAYMENT -> {
                        currentCreditSum = currentTotalRemainingDebtSum
                        currentCreditTerm -= currentCreditPeriodIndex
                        currentCreditPeriodIndex = 0
                        currentCreditMinPaymentSum = 0.0
                    }
                    EarlyPaymentType.EARLY_DECREASE_TERM -> {
                        currentCreditMinPaymentSum = getPayment(
                            creditSum = currentTotalRemainingDebtSum,
                            creditMinPaymentSum = currentCreditMinPaymentSum,
                            creditRate = currentCreditRate,
                            creditTerm = currentCreditTerm,
                            creditRatePeriod = creditRatePeriod,
                            creditPaymentPeriod = creditPaymentPeriod,
                            currentTotalRemainingDebtSum = currentTotalRemainingDebtSum,
                            currentTotalAccruedPercentSum = 0.0
                        )
                        currentCreditTerm = getTerm(
                            creditSum = currentTotalRemainingDebtSum,
                            creditPaymentSum = currentCreditMinPaymentSum,
                            creditRate = currentCreditRate,
                            creditTerm = currentCreditTerm,
                            creditRatePeriod = creditRatePeriod,
                            creditPaymentPeriod = creditPaymentPeriod
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
            val currentCreditRateChanged = creditRateChangesList.find { currentCreditRate != it.changedRate }
            if (currentCreditRateChanged != null) {
                currentCreditRate = currentCreditRateChanged.changedRate
                val calculation = CreditCalculationEntity.RateChangesCreditCalculationEntity(
                    changedCreditRate = currentCreditRate,
                    calculationDate = currentCalculationDate,
                    currentCreditRemainingDebtSum = currentTotalRemainingDebtSum,
                    currentCreditAccruedPercentSum = currentTotalAccruedPercentSum
                )
                all.add(calculation)
            }
        }
        return all
    }

    private fun getAccruedPercentSum(calculationDate: Calendar, creditRate: Double, creditRatePeriod: CreditPeriodType, totalRemainingDebtSum: Double): Double {
        val p = getPForDate(calculationDate, creditRate, creditRatePeriod)
        return totalRemainingDebtSum * p
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