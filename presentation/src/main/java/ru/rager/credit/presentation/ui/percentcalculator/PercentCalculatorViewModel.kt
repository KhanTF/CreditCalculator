package ru.rager.credit.presentation.ui.percentcalculator

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.utils.CreditValidator.isCreditPaymentValid
import ru.rager.credit.domain.utils.CreditValidator.isCreditSumValid
import ru.rager.credit.domain.utils.CreditValidator.isCreditTermValid
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.util.NEGATIVE_DOUBLE
import ru.rager.credit.presentation.util.livedata.combinedLiveData
import ru.rager.credit.presentation.util.map
import javax.inject.Inject

class PercentCalculatorViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory
) : BaseViewModel(router) {

    private val creditPaymentFrequencyList = listOf(
        CreditPeriodType.EVERY_MONTH,
        CreditPeriodType.EVERY_QUARTER,
        CreditPeriodType.EVERY_YEAR,
    )
    val creditRateTypeSelectedLiveData = MutableLiveData<Int>()
    val creditRateTypeList = CreditRateType.values().toList()
    val creditRateFrequencySelectedLiveData = MutableLiveData(0)
    val creditRateFrequencyList = listOf(
        CreditPeriodType.EVERY_YEAR,
        CreditPeriodType.EVERY_QUARTER,
        CreditPeriodType.EVERY_MONTH
    )
    val creditPaymentFrequencySelectedLiveData = MutableLiveData(0)
    val creditPaymentFrequencyListLiveData = creditRateFrequencySelectedLiveData.map { creditRateFrequencySelected ->
        val creditRateFrequency = creditRateFrequencyList.getOrNull(creditRateFrequencySelected) ?: return@map listOf(CreditPeriodType.EVERY_MONTH)
        creditPaymentFrequencyList.filter { it.value <= creditRateFrequency.value }
    }
    val creditSumLiveData = MutableLiveData<String>()
    val creditMonthPaymentLiveData = MutableLiveData<String>()
    val creditTermLiveData = MutableLiveData<String>()

    val creditCalculationType = creditRateTypeSelectedLiveData.map {
        creditRateTypeList[it]
    }
    val isCalculateAvailable = combinedLiveData(
        creditRateTypeSelectedLiveData,
        creditSumLiveData,
        creditMonthPaymentLiveData,
        creditTermLiveData
    ).map { (selectedType: Int?, creditSum: String?, monthPayment: String?, creditTerm: String?) ->
        val isSelectedTypeValid = selectedType != null && selectedType >= 0 && selectedType < creditRateTypeList.size
        val creditSumValue = creditSum?.toDoubleOrNull()
        val creditMonthPaymentValue = monthPayment?.toDoubleOrNull()
        val creditTermValue = creditTerm?.toIntOrNull()
        isSelectedTypeValid && isCreditSumValid(creditSumValue) && isCreditPaymentValid(creditMonthPaymentValue) && isCreditTermValid(creditTermValue)
    }
    val creditMinPayment = combinedLiveData(
        creditSumLiveData,
        creditTermLiveData
    ).map { (creditSum: String?, creditTerm: String?) ->
        val creditSumValue = creditSum?.toDoubleOrNull()
        val creditTermValue = creditTerm?.toIntOrNull()
        if (creditSumValue != null && creditTermValue != null) {
            creditSumValue / creditTermValue
        } else {
            NEGATIVE_DOUBLE
        }
    }

    fun onCalculate() {
        /*val creditRateTypeSelected = creditRateTypeSelectedLiveData.value ?: return
        val creditRateType = creditRateTypeList.getOrNull(creditRateTypeSelected) ?: return

        val creditRateFrequencySelectedSelected = creditRateFrequencySelectedLiveData.value ?: return
        val creditRateFrequencyType = creditRateFrequencyList.getOrNull(creditRateFrequencySelectedSelected) ?: return

        val creditPaymentFrequencySelected = creditPaymentFrequencySelectedLiveData.value ?: return
        val creditPaymentFrequencyType = creditPaymentFrequencyList.getOrNull(creditPaymentFrequencySelected) ?: return

        val creditSum = creditSumLiveData.getDoubleValue() ?: return
        val creditMonthPayment = creditMonthPaymentLiveData.getDoubleValue() ?: return
        val creditTerm = creditTermLiveData.getIntValue() ?: return
        getCreditRateUseCase
            .getCreditRateSingle(
                creditRateType = creditRateType,
                creditSum = creditSum,
                creditFirstPayment = creditMonthPayment,
                creditTerm = creditTerm,
                creditRatePeriod = creditRateFrequencyType,
                creditPaymentPeriod = creditPaymentFrequencyType
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ creditRate ->
                router.replaceScreen(
                    screenFactory.getCalculationScreen(
                        CalculationViewModel.Parameters(
                            creditRateType = creditRateType,
                            creditSum = creditSum,
                            creditRate = creditRate,
                            creditTerm = creditTerm,
                            creditPaymentPeriodType = creditPaymentFrequencyType,
                            creditRatePeriodType = creditRateFrequencyType
                        )
                    )
                )
            }, { e ->
                postEvent(Event.UnknownError)
                e.printStackTrace()
            })
            .disposeOnClear()*/
    }

}