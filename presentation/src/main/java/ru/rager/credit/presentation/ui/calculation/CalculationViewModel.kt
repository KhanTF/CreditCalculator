package ru.rager.credit.presentation.ui.calculation

import com.github.terrakok.cicerone.Router
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.util.NEGATIVE_DOUBLE
import ru.rager.credit.presentation.util.zeroIfNegative
import javax.inject.Inject

class CalculationViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory,
    private val creditCalculationEntity: CreditCalculationEntity
) : BaseViewModel(router) {

    val creditCalculationType = creditCalculationEntity.creditCalculationType
    val creditSum = creditCalculationEntity.creditSum
    val creditRate = creditCalculationEntity.creditRate
    val creditTerm = creditCalculationEntity.creditTerm
    val creditPaymentList = creditCalculationEntity.creditCalculationPaymentList
    val creditSumPayments = creditCalculationEntity.getSumPayments()
    val creditOverpayments = creditCalculationEntity.getOverpayments()
    val creditMonthPayment = when {
        creditCalculationEntity.isAnnuity() -> creditCalculationEntity.creditCalculationPaymentList.firstOrNull()?.creditPayment ?: NEGATIVE_DOUBLE
        else -> NEGATIVE_DOUBLE
    }
    val creditFirstPayment = when {
        creditCalculationEntity.isDifferentiated() -> creditCalculationEntity.creditCalculationPaymentList.firstOrNull()?.creditPayment ?: NEGATIVE_DOUBLE
        else -> NEGATIVE_DOUBLE
    }
    val creditLastPayment = when {
        creditCalculationEntity.isDifferentiated() -> creditCalculationEntity.creditCalculationPaymentList.lastOrNull()?.creditPayment ?: NEGATIVE_DOUBLE
        else -> NEGATIVE_DOUBLE
    }

}