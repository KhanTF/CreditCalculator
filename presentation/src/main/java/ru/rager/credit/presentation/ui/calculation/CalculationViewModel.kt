package ru.rager.credit.presentation.ui.calculation

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.rager.credit.domain.entity.CreditDateCalculationEntity
import ru.rager.credit.domain.usecase.CreditCalculatorInteractor
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.ui.base.ErrorEvent
import ru.rager.credit.presentation.util.livedata.combinedNotNullLiveData
import ru.rager.credit.presentation.util.livedata.mapImmediate
import javax.inject.Inject

class CalculationViewModel @Inject constructor(
    creditCalculatorInteractor: CreditCalculatorInteractor,
    arguments: CalculationFragmentArgs
) : BaseViewModel() {

    private val creditParametersEntity = arguments.parameters
    val calculationId = MutableLiveData(creditParametersEntity.id)
    val calculationName = MutableLiveData(creditParametersEntity.name)
    val creditSum = MutableLiveData(creditParametersEntity.creditSum)
    val creditTerm = MutableLiveData(creditParametersEntity.creditTerm)
    val creditRate = MutableLiveData(creditParametersEntity.creditRate)
    val ratePeriod = MutableLiveData(creditParametersEntity.creditRatePeriod)
    val rateType = MutableLiveData(creditParametersEntity.creditRateType)
    val paymentPeriod = MutableLiveData(creditParametersEntity.creditPaymentPeriod)
    val dateCalculationList = MutableLiveData<List<CreditDateCalculationEntity>>()
    val firstPayment = dateCalculationList.mapImmediate { calculationList ->
        calculationList
            .find { it is CreditDateCalculationEntity.SchedulePaymentCreditDateCalculationEntity }
            ?.let { it as CreditDateCalculationEntity.SchedulePaymentCreditDateCalculationEntity }
            ?.payment ?: 0.0
    }
    val lastPayment = dateCalculationList.mapImmediate { calculationList ->
        calculationList
            .findLast { it is CreditDateCalculationEntity.SchedulePaymentCreditDateCalculationEntity }
            ?.let { it as CreditDateCalculationEntity.SchedulePaymentCreditDateCalculationEntity }
            ?.payment ?: 0.0
    }
    val sumPayments = dateCalculationList.mapImmediate { calculationList ->
        calculationList.sumByDouble {
            when (it) {
                is CreditDateCalculationEntity.SchedulePaymentCreditDateCalculationEntity -> it.payment
                is CreditDateCalculationEntity.EarlyPaymentCreditDateCalculationEntity -> it.payment
                else -> 0.0
            }
        }
    }
    val overpayments = combinedNotNullLiveData(
        sumPayments,
        creditSum
    ).mapImmediate { (creditSumPayments: Double, creditSum: Double) ->
        creditSumPayments - creditSum
    }

    init {
        creditCalculatorInteractor
            .getCalculationListSingle(creditParametersEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(Throwable::printStackTrace)
            .subscribe({
                dateCalculationList.value = it
            }, {
                postViewModelEvent(ErrorEvent(it))
            })
            .disposeOnClear()
    }

    fun onOpenDeleteCalculationConfirmation() {
        val creditCalculationName = calculationName.value ?: return
    }

    fun onOpenSaveCalculation() {
    }

    fun onDeleteCalculation() {

    }

    fun onSaveCalculation(calculationName: String) {

    }

}