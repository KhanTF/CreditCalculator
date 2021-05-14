package ru.rager.credit.presentation.ui.paymentcalculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.github.terrakok.cicerone.Router
import ru.rager.credit.domain.entity.CreditCalculationPercentType
import ru.rager.credit.domain.entity.CreditPaymentEntity
import ru.rager.credit.domain.usecase.GetCreditCalculationResultUseCase
import ru.rager.credit.presentation.model.CalculationParameters
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.util.livedata.combinedNotNullLiveData
import javax.inject.Inject

class PaymentCalculatorViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory,
    private val getCreditCalculationResultUseCase: GetCreditCalculationResultUseCase
) : BaseViewModel(router) {

    val typeArray = MutableLiveData<List<String>>().apply {
        value = listOf("Аннуитетный", "Дифференцированный")
    }
    val selectedType = MutableLiveData<Int>()
    val creditAmountLiveData = MutableLiveData<String>()
    val creditRateLiveData = MutableLiveData<String>()
    val creditTermLiveData = MutableLiveData<String>()

    fun onCalculate() {
        val calculationParameters = CalculationParameters(
            CreditCalculationPercentType.ANNUITY,
            creditAmountLiveData.value?.toDoubleOrNull() ?: return,
            creditRateLiveData.value?.toDoubleOrNull() ?: return,
            creditTermLiveData.value?.toInt() ?: return
        )
        router.navigateTo(screenFactory.getCalculationResultScreen(calculationParameters))
    }

}