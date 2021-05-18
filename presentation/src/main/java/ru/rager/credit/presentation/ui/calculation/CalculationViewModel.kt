package ru.rager.credit.presentation.ui.calculation

import com.github.terrakok.cicerone.Router
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.util.NEGATIVE_DOUBLE
import javax.inject.Inject

class CalculationViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory,
    private val creditCalculationEntity: CreditCalculationEntity
) : BaseViewModel(router) {

    val creditCalculationType = creditCalculationEntity.creditCalculationType
    val creditSum = creditCalculationEntity.creditCalculationParameter.creditSum
    val creditRate = creditCalculationEntity.creditCalculationParameter.creditRate
    val creditTerm = creditCalculationEntity.creditCalculationParameter.creditTerm
    val creditPaymentList = creditCalculationEntity.creditCalculationPaymentList
    val creditSumPayments = creditCalculationEntity.getSumPayments()
    val creditOverpayments = creditCalculationEntity.getOverPayments()
    val creditMonthPayment = when (creditCalculationEntity.creditCalculationType) {
        CreditCalculationType.ANNUITY -> creditCalculationEntity.creditCalculationPaymentList.firstOrNull()?.creditPayment ?: NEGATIVE_DOUBLE
        else -> NEGATIVE_DOUBLE
    }
    val creditFirstPayment = when (creditCalculationEntity.creditCalculationType) {
        CreditCalculationType.DIFFERENTIATED -> creditCalculationEntity.creditCalculationPaymentList.firstOrNull()?.creditPayment ?: NEGATIVE_DOUBLE
        else -> NEGATIVE_DOUBLE
    }
    val creditLastPayment = when (creditCalculationEntity.creditCalculationType) {
        CreditCalculationType.DIFFERENTIATED -> creditCalculationEntity.creditCalculationPaymentList.lastOrNull()?.creditPayment ?: NEGATIVE_DOUBLE
        else -> NEGATIVE_DOUBLE
    }

}