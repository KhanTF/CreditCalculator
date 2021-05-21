package ru.rager.credit.presentation.ui.paymentcalculator

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.utils.CreditValidator.isCreditRateValid
import ru.rager.credit.domain.utils.CreditValidator.isCreditSumValid
import ru.rager.credit.domain.utils.CreditValidator.isCreditTermValid
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.ui.calculation.CalculationViewModel
import ru.rager.credit.presentation.util.getDoubleValue
import ru.rager.credit.presentation.util.getIntValue
import ru.rager.credit.presentation.util.livedata.combinedLiveData
import ru.rager.credit.presentation.util.map
import javax.inject.Inject

class PaymentCalculatorViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory
) : BaseViewModel(router) {

    private val creditPaymentFrequencyList = listOf(
        CreditFrequencyType.EVERY_MONTH,
        CreditFrequencyType.EVERY_QUARTER,
        CreditFrequencyType.EVERY_YEAR,
    )
    val creditRateSelectedLiveData = MutableLiveData(0)
    val creditRateList = CreditRateType.values().toList()
    val creditRateFrequencySelectedLiveData = MutableLiveData(0)
    val creditRateFrequencyList = listOf(
        CreditFrequencyType.EVERY_YEAR,
        CreditFrequencyType.EVERY_QUARTER,
        CreditFrequencyType.EVERY_MONTH
    )
    val creditPaymentFrequencySelectedLiveData = MutableLiveData(0)
    val creditPaymentFrequencyListLiveData = creditRateFrequencySelectedLiveData.map { creditRateFrequencySelected ->
        val creditRateFrequency = creditRateFrequencyList.getOrNull(creditRateFrequencySelected) ?: return@map listOf(CreditFrequencyType.EVERY_MONTH)
        creditPaymentFrequencyList.filter { it.value <= creditRateFrequency.value }
    }
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

        val creditRateFrequencySelectedSelected = creditRateFrequencySelectedLiveData.value ?: return
        val creditRateFrequencyType = creditRateFrequencyList.getOrNull(creditRateFrequencySelectedSelected) ?: return

        val creditPaymentFrequencySelected = creditPaymentFrequencySelectedLiveData.value ?: return
        val creditPaymentFrequencyType = creditPaymentFrequencyList.getOrNull(creditPaymentFrequencySelected) ?: return

        val creditSum = creditSumLiveData.getDoubleValue() ?: return
        val creditRate = creditRateLiveData.getDoubleValue() ?: return
        val creditTerm = creditTermLiveData.getIntValue() ?: return
        router.replaceScreen(
            screenFactory.getCalculationScreen(
                CalculationViewModel.Parameters(
                    creditRateType = creditRateType,
                    creditSum = creditSum,
                    creditRate = creditRate,
                    creditTerm = creditTerm,
                    creditRateFrequencyType = creditRateFrequencyType,
                    creditPaymentFrequencyType = creditPaymentFrequencyType
                )
            )
        )
    }

}