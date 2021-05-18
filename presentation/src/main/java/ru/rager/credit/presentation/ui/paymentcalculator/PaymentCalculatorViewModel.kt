package ru.rager.credit.presentation.ui.paymentcalculator

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import ru.rager.credit.domain.usecase.CalculatePaymentUseCase
import ru.rager.credit.domain.utils.CreditConstants.isCreditRateValid
import ru.rager.credit.domain.utils.CreditConstants.isCreditSumValid
import ru.rager.credit.domain.utils.CreditConstants.isCreditTermValid
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.util.getDoubleValue
import ru.rager.credit.presentation.util.getIntValue
import ru.rager.credit.presentation.util.livedata.combinedLiveData
import ru.rager.credit.presentation.util.map
import javax.inject.Inject

class PaymentCalculatorViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory,
    private val calculatePaymentUseCase: CalculatePaymentUseCase
) : BaseViewModel(router) {

    val creditCalculationTypeList = CreditCalculationType.values().toList()
    val creditCalculationTypeSelectedLiveData = MutableLiveData<Int>()
    val creditSumLiveData = MutableLiveData<String>()
    val creditRateLiveData = MutableLiveData<String>()
    val creditTermLiveData = MutableLiveData<String>()
    val isCalculateAvailable = combinedLiveData(
        creditCalculationTypeSelectedLiveData,
        creditSumLiveData,
        creditRateLiveData,
        creditTermLiveData
    ).map { (selectedType: Int?, creditSum: String?, creditRate: String?, creditTerm: String?) ->
        val isSelectedTypeValid = selectedType != null && selectedType >= 0 && selectedType < creditCalculationTypeList.size
        val creditSumValue = creditSum?.toDoubleOrNull()
        val creditRateValue = creditRate?.toDoubleOrNull()
        val creditTermValue = creditTerm?.toIntOrNull()
        isSelectedTypeValid && isCreditSumValid(creditSumValue) && isCreditRateValid(creditRateValue) && isCreditTermValid(creditTermValue)
    }

    fun onCalculate() {
        val creditCalculationPercentTypeSelected = creditCalculationTypeSelectedLiveData.value ?: return
        val creditCalculationPercentType = creditCalculationTypeList.getOrNull(creditCalculationPercentTypeSelected) ?: return
        val creditSum = creditSumLiveData.getDoubleValue() ?: return
        val creditRate = creditRateLiveData.getDoubleValue() ?: return
        val creditTerm = creditTermLiveData.getIntValue() ?: return
        val creditCalculationEntity = when (creditCalculationPercentType) {
            CreditCalculationType.ANNUITY -> calculatePaymentUseCase.getAnnuityCalculation(
                creditSum, creditRate, creditTerm
            )
            CreditCalculationType.DIFFERENTIATED -> calculatePaymentUseCase.getDifferentiatedCalculation(
                creditSum, creditRate, creditTerm
            )
        }
        router.navigateTo(screenFactory.getCalculationScreen(creditCalculationEntity))
    }

}