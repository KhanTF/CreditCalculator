package ru.rager.credit.presentation.ui.percentcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.usecase.CreditCalculatorInteractor
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.ui.base.ErrorEvent
import ru.rager.credit.presentation.util.livedata.SingleLiveData
import ru.rager.credit.presentation.util.livedata.combinedLiveData
import ru.rager.credit.presentation.util.livedata.combinedNotNullLiveData
import java.util.*
import javax.inject.Inject

class PercentCalculatorViewModel @Inject constructor(private val creditCalculatorInteractor: CreditCalculatorInteractor) : BaseViewModel() {

    val creditRateTypeSelected: MutableLiveData<Int> = MutableLiveData()
    val creditRateTypeList = MutableLiveData(CreditRateType.values().toList())
    val creditRateType: LiveData<CreditRateType> = combinedNotNullLiveData(
        creditRateTypeList,
        creditRateTypeSelected
    ).map { (creditRateTypeList: List<CreditRateType>, creditRateTypeSelected: Int) ->
        creditRateTypeList[creditRateTypeSelected]
    }
    val creditRatePeriodSelected: MutableLiveData<Int> = MutableLiveData()
    val creditRatePeriodList: LiveData<List<CreditPeriodType>> = MutableLiveData(
        listOf(CreditPeriodType.EVERY_YEAR, CreditPeriodType.EVERY_QUARTER, CreditPeriodType.EVERY_MONTH)
    )
    val creditRatePeriod: LiveData<CreditPeriodType> = combinedNotNullLiveData(
        creditRatePeriodList,
        creditRatePeriodSelected
    ).map { (creditRatePeriodList: List<CreditPeriodType>, creditRatePeriodSelected: Int) ->
        creditRatePeriodList[creditRatePeriodSelected]
    }
    val creditPaymentPeriodSelected: MutableLiveData<Int> = MutableLiveData()
    val creditPaymentPeriodList: LiveData<List<CreditPeriodType>> = MutableLiveData(
        listOf(CreditPeriodType.EVERY_MONTH, CreditPeriodType.EVERY_QUARTER, CreditPeriodType.EVERY_YEAR)
    )
    val creditPaymentPeriod: LiveData<CreditPeriodType> = combinedNotNullLiveData(
        creditPaymentPeriodList,
        creditPaymentPeriodSelected
    ).map { (creditPaymentPeriodList: List<CreditPeriodType>, creditPaymentPeriodSelected: Int) ->
        creditPaymentPeriodList[creditPaymentPeriodSelected]
    }
    val creditPayment: MutableLiveData<String> = MutableLiveData()
    val creditSum: MutableLiveData<String> = MutableLiveData()
    val creditTerm: MutableLiveData<String> = MutableLiveData()
    val isCalculateAvailable = combinedLiveData(
        creditRateType,
        creditRatePeriod,
        creditPaymentPeriod,
        creditPayment,
        creditSum,
        creditTerm
    ).map { (creditRateType: CreditRateType?, creditRatePeriod: CreditPeriodType?, creditPaymentPeriod: CreditPeriodType?, creditMonthPayment: String?, creditSum: String?, creditTerm: String?) ->
        creditRateType != null && creditRatePeriod != null && creditPaymentPeriod != null
    }
    val creditMinPayment = combinedLiveData(
        creditSum,
        creditTerm
    ).map { (creditSum: String?, creditTerm: String?) ->
        val creditSumValue = creditSum?.toDoubleOrNull()
        val creditTermValue = creditTerm?.toIntOrNull()
        if (creditSumValue != null && creditTermValue != null) {
            creditSumValue / creditTermValue
        } else {
            -1.0
        }
    }

    val openCalculation = SingleLiveData<CreditParametersEntity>()

    fun onCalculate() {
        val creditRateType = creditRateType.value ?: return
        val creditRatePeriod = creditRatePeriod.value ?: return
        val creditPaymentPeriod = creditPaymentPeriod.value ?: return
        val creditSum = creditSum.value?.toDoubleOrNull() ?: return
        val creditTerm = creditTerm.value?.toIntOrNull() ?: return
        val creditPayment = creditPayment.value?.toDoubleOrNull() ?: return
        creditCalculatorInteractor
            .getCreditRateSingle(
                creditSum = creditSum,
                creditPaymentSum = creditPayment,
                creditRateType = creditRateType,
                creditTerm = creditTerm,
                creditRatePeriod = creditRatePeriod,
                creditPaymentPeriod = creditPaymentPeriod
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(Throwable::printStackTrace)
            .subscribe({ creditRate ->
                val parameters = CreditParametersEntity(
                    id = 0L,
                    name = "",
                    creditStart = Calendar.getInstance(),
                    creditSum = creditSum,
                    creditRate = creditRate,
                    creditRateType = creditRateType,
                    creditTerm = creditTerm,
                    creditSkipWeekend = false,
                    creditRatePeriod = creditRatePeriod,
                    creditPaymentPeriod = creditPaymentPeriod,
                    creditEarlyPaymentEntityList = emptyList(),
                    creditRateChangesList = emptyList()
                )
                openCalculation.setValue(parameters)
            }, {
                postViewModelEvent(ErrorEvent(it))
            })
            .disposeOnClear()
    }

}