package ru.rager.credit.presentation.ui.paymentcalculator

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.domain.entity.enums.CreditPeriodType
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

    private val creditPaymentPeriodList = listOf(
        CreditPeriodType.EVERY_MONTH,
        CreditPeriodType.EVERY_QUARTER,
        CreditPeriodType.EVERY_YEAR,
    )

    val creditSumLiveData = MutableLiveData<String>()
    val creditRateLiveData = MutableLiveData<String>()
    val creditTermLiveData = MutableLiveData<String>()

    val creditRateSelectedLiveData = MutableLiveData(0)
    val creditRateList = CreditRateType.values().toList()
    val creditRatePeriodSelectedLiveData = MutableLiveData(0)
    val creditRateFrequencyList = listOf(
        CreditPeriodType.EVERY_YEAR,
        CreditPeriodType.EVERY_QUARTER,
        CreditPeriodType.EVERY_MONTH
    )
    val creditPaymentPeriodSelectedLiveData = MutableLiveData(0)
    val creditPaymentPeriodListLiveData = creditRatePeriodSelectedLiveData.map { creditRateFrequencySelected ->
        val creditRateFrequency = creditRateFrequencyList.getOrNull(creditRateFrequencySelected) ?: return@map listOf(CreditPeriodType.EVERY_MONTH)
        creditPaymentPeriodList.filter { it.value <= creditRateFrequency.value }
    }
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

        val creditRatePeriodSelectedSelected = creditRatePeriodSelectedLiveData.value ?: return
        val creditRatePeriodType = creditRateFrequencyList.getOrNull(creditRatePeriodSelectedSelected) ?: return

        val creditPaymentPeriodSelected = creditPaymentPeriodSelectedLiveData.value ?: return
        val creditPaymentPeriod = creditPaymentPeriodList.getOrNull(creditPaymentPeriodSelected) ?: return

        val creditSum = creditSumLiveData.getDoubleValue() ?: return
        val creditRate = creditRateLiveData.getDoubleValue() ?: return
        val creditTerm = creditTermLiveData.getIntValue() ?: return

        val parameters = CreditParametersEntity.Builder()
            .setCreditSum(creditSum)
            .setCreditRate(creditRate)
            .setCreditRateType(creditRateType)
            .setCreditTerm(creditTerm)
            .setCreditPaymentPeriod(creditPaymentPeriod)
            .setCreditRatePeriod(creditRatePeriodType)
            .build()
        router.navigateTo(screenFactory.getCalculationScreen(CalculationViewModel.Parameters(parameters)))
    }

}