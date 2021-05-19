package ru.rager.credit.presentation.ui.percentcalculator

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import ru.rager.credit.domain.exceptions.PaymentTooMuchException
import ru.rager.credit.domain.exceptions.PaymentTooSmallException
import ru.rager.credit.domain.usecase.GetCalculationParameterUseCase
import ru.rager.credit.domain.utils.CreditConstants.isCreditPaymentValid
import ru.rager.credit.domain.utils.CreditConstants.isCreditSumValid
import ru.rager.credit.domain.utils.CreditConstants.isCreditTermValid
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.util.NEGATIVE_DOUBLE
import ru.rager.credit.presentation.util.getDoubleValue
import ru.rager.credit.presentation.util.getIntValue
import ru.rager.credit.presentation.util.livedata.combinedLiveData
import ru.rager.credit.presentation.util.map
import javax.inject.Inject

class PercentCalculatorViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory,
    private val getCalculationParameterUseCase: GetCalculationParameterUseCase
) : BaseViewModel(router) {

    val creditCalculationTypeList = CreditCalculationType.values().toList()
    val creditCalculationTypeSelectedLiveData = MutableLiveData<Int>()
    val creditSumLiveData = MutableLiveData<String>()
    val creditMonthPaymentLiveData = MutableLiveData<String>()
    val creditTermLiveData = MutableLiveData<String>()

    val creditCalculationType = creditCalculationTypeSelectedLiveData.map {
        creditCalculationTypeList[it]
    }
    val isCalculateAvailable = combinedLiveData(
        creditCalculationTypeSelectedLiveData,
        creditSumLiveData,
        creditMonthPaymentLiveData,
        creditTermLiveData
    ).map { (selectedType: Int?, creditSum: String?, monthPayment: String?, creditTerm: String?) ->
        val isSelectedTypeValid = selectedType != null && selectedType >= 0 && selectedType < creditCalculationTypeList.size
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
        val creditCalculationPercentTypeSelected = creditCalculationTypeSelectedLiveData.value ?: return
        val creditCalculationPercentType = creditCalculationTypeList.getOrNull(creditCalculationPercentTypeSelected) ?: return
        val creditSum = creditSumLiveData.getDoubleValue() ?: return
        val creditMonthPayment = creditMonthPaymentLiveData.getDoubleValue() ?: return
        val creditTerm = creditTermLiveData.getIntValue() ?: return
        getCalculationParameterUseCase
            .getCreditCalculationParameter(creditCalculationPercentType, creditSum, creditTerm, creditMonthPayment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ creditCalculationParameter ->
                router.navigateTo(screenFactory.getCalculationScreen(creditCalculationParameter))
            }, { e ->
                when (e) {
                    is PaymentTooSmallException -> postEvent(PaymentTooSmall)
                    is PaymentTooMuchException -> postEvent(PaymentTooMuch)
                }
            })
            .disposeOnClear()
    }

    object PaymentTooMuch : Event

    object PaymentTooSmall : Event

}