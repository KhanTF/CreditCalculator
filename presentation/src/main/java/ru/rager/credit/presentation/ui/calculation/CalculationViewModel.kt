package ru.rager.credit.presentation.ui.calculation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.usecase.CreditCalculatorInteractor
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.ui.base.ErrorEvent
import ru.rager.credit.presentation.ui.base.ViewModelEvent
import ru.rager.credit.presentation.util.livedata.combinedNotNullLiveData
import javax.inject.Inject

class CalculationViewModel @Inject constructor(
    creditCalculatorInteractor: CreditCalculatorInteractor,
    arguments: CalculationFragmentArgs
) : BaseViewModel() {

    private val creditParametersEntity = arguments.parameters
    val creditCalculationId = MutableLiveData(creditParametersEntity.id)
    val creditCalculationName = MutableLiveData(creditParametersEntity.name)
    val creditSum = MutableLiveData(creditParametersEntity.creditSum)
    val creditTerm = MutableLiveData(creditParametersEntity.creditTerm)
    val creditRate = MutableLiveData(creditParametersEntity.creditRate)
    val creditRatePeriod = MutableLiveData(creditParametersEntity.creditRatePeriod)
    val creditRateType = MutableLiveData(creditParametersEntity.creditRateType)
    val creditPaymentPeriod = MutableLiveData(creditParametersEntity.creditPaymentPeriod)
    val creditCalculationList = MutableLiveData<List<CreditCalculationEntity>>()
    val creditFirstPayment = creditCalculationList.map { calculationList ->
        calculationList
            .find { it is CreditCalculationEntity.SchedulePaymentCreditCalculationEntity }
            ?.let { it as CreditCalculationEntity.SchedulePaymentCreditCalculationEntity }
            ?.payment ?: 0.0
    }

    val creditLastPayment = creditCalculationList.map { calculationList ->
        calculationList
            .findLast { it is CreditCalculationEntity.SchedulePaymentCreditCalculationEntity }
            ?.let { it as CreditCalculationEntity.SchedulePaymentCreditCalculationEntity }
            ?.payment ?: 0.0
    }

    val creditSumPayments = creditCalculationList.map { calculationList ->
        calculationList.sumByDouble {
            when (it) {
                is CreditCalculationEntity.SchedulePaymentCreditCalculationEntity -> it.payment
                is CreditCalculationEntity.EarlyPaymentCreditCalculationEntity -> it.payment
                else -> 0.0
            }
        }
    }

    val creditOverpayments = combinedNotNullLiveData(
        creditSumPayments,
        creditSum
    ).map { (creditSumPayments: Double, creditSum: Double) ->
        creditSumPayments - creditSum
    }

    init {
        creditCalculatorInteractor
            .getCalculationListSingle(creditParametersEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(Throwable::printStackTrace)
            .subscribe({
                creditCalculationList.value = it
            }, {
                postViewModelEvent(ErrorEvent(it))
            })
            .disposeOnClear()
    }

    fun onOpenDeleteCalculationConfirmation() {
        val creditCalculationName = creditCalculationName.value ?: return
        postViewModelEvent(OpenDeleteCalculationConfirmationViewModelEvent(creditCalculationName))
    }

    fun onOpenSaveCalculation() {
        postViewModelEvent(OpenSaveCalculationViewModelEvent())
    }

    fun onDeleteCalculation() {

    }

    fun onSaveCalculation(calculationName: String) {

    }


    class OpenDeleteCalculationConfirmationViewModelEvent(val calculationName: String) : ViewModelEvent.ExtendedEvent()

    class OpenSaveCalculationViewModelEvent : ViewModelEvent.ExtendedEvent()

}