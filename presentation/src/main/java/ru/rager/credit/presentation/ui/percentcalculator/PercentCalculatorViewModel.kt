package ru.rager.credit.presentation.ui.percentcalculator

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.exceptions.CalculationException
import ru.rager.credit.domain.usecase.GetCreditRateUseCase
import ru.rager.credit.domain.utils.CreditValidator.isCreditPaymentValid
import ru.rager.credit.domain.utils.CreditValidator.isCreditSumValid
import ru.rager.credit.domain.utils.CreditValidator.isCreditTermValid
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.ui.base.events.Event
import ru.rager.credit.presentation.util.NEGATIVE_DOUBLE
import ru.rager.credit.presentation.util.getDoubleValue
import ru.rager.credit.presentation.util.getIntValue
import ru.rager.credit.presentation.util.livedata.combinedLiveData
import ru.rager.credit.presentation.util.map
import javax.inject.Inject

class PercentCalculatorViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory,
    private val getCreditRateUseCase: GetCreditRateUseCase
) : BaseViewModel(router) {

    val creditRateTypeList = CreditRateType.values().toList()
    val creditRateTypeSelectedLiveData = MutableLiveData<Int>()
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
        val creditRateTypeSelected = creditRateTypeSelectedLiveData.value ?: return
        val creditRateType = creditRateTypeList.getOrNull(creditRateTypeSelected) ?: return
        val creditSum = creditSumLiveData.getDoubleValue() ?: return
        val creditMonthPayment = creditMonthPaymentLiveData.getDoubleValue() ?: return
        val creditTerm = creditTermLiveData.getIntValue() ?: return
        getCreditRateUseCase
            .getCreditRateSingle(creditRateType, creditSum, creditMonthPayment, creditTerm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ creditRate ->
                router.navigateTo(
                    screenFactory.getCalculationScreen(
                        creditRateType = creditRateType,
                        creditSum = creditSum,
                        creditRate = creditRate,
                        creditTerm = creditTerm
                    )
                )
            }, { e ->
                when (e) {
                    is CalculationException -> postEvent(Event.CalculationError)
                    else -> postEvent(Event.UnknownError)
                }
                e.printStackTrace()
            })
            .disposeOnClear()
    }

}