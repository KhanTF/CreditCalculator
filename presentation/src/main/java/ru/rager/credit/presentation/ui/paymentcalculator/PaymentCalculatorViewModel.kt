package ru.rager.credit.presentation.ui.paymentcalculator

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.utils.CreditValidator.isCreditRateValid
import ru.rager.credit.domain.utils.CreditValidator.isCreditSumValid
import ru.rager.credit.domain.utils.CreditValidator.isCreditTermValid
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.util.getDoubleValue
import ru.rager.credit.presentation.util.getIntValue
import ru.rager.credit.presentation.util.livedata.combinedLiveData
import ru.rager.credit.presentation.util.map
import javax.inject.Inject

class PaymentCalculatorViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory
) : BaseViewModel(router) {

    val creditRateList = CreditRateType.values().toList()
    val creditRateSelectedLiveData = MutableLiveData<Int>()
    val creditSumLiveData = MutableLiveData<String>()
    val creditRateLiveData = MutableLiveData<String>()
    val creditTermLiveData = MutableLiveData<String>()
    val isCalculateAvailable = combinedLiveData(
        creditRateSelectedLiveData,
        creditSumLiveData,
        creditRateLiveData,
        creditTermLiveData
    ).map { (selectedType: Int?, creditSum: String?, creditRate: String?, creditTerm: String?) ->
        val isSelectedTypeValid = selectedType != null && selectedType >= 0 && selectedType < creditRateList.size
        val creditSumValue = creditSum?.toDoubleOrNull()
        val creditRateValue = creditRate?.toDoubleOrNull()
        val creditTermValue = creditTerm?.toIntOrNull()
        isSelectedTypeValid && isCreditSumValid(creditSumValue) && isCreditRateValid(creditRateValue) && isCreditTermValid(creditTermValue)
    }

    fun onCalculate() {
        val creditRateTypeSelected = creditRateSelectedLiveData.value ?: return
        val creditRateType = creditRateList.getOrNull(creditRateTypeSelected) ?: return
        val creditSum = creditSumLiveData.getDoubleValue() ?: return
        val creditRate = creditRateLiveData.getDoubleValue() ?: return
        val creditTerm = creditTermLiveData.getIntValue() ?: return
        router.navigateTo(
            screenFactory.getCalculationScreen(
                creditRateType = creditRateType,
                creditSum = creditSum,
                creditRate = creditRate,
                creditTerm = creditTerm
            )
        )
    }

}