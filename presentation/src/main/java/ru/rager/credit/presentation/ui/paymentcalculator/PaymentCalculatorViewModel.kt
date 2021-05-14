package ru.rager.credit.presentation.ui.paymentcalculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.github.terrakok.cicerone.Router
import ru.rager.credit.domain.entity.CreditPaymentEntity
import ru.rager.credit.domain.usecase.GetPaymentScheduleUseCase
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.util.livedata.combinedNotNullLiveData
import javax.inject.Inject

class PaymentCalculatorViewModel @Inject constructor(
    private val router: Router,
    private val getPaymentScheduleUseCase: GetPaymentScheduleUseCase
) : BaseViewModel(router) {

    val typeArray = MutableLiveData<List<String>>().apply {
        value = listOf("Аннуитетный", "Дифференцированный")
    }
    val selectedType = MutableLiveData<Int>()
    val creditAmountLiveData = MutableLiveData<String>()
    val creditRateLiveData = MutableLiveData<String>()
    val creditTermLiveData = MutableLiveData<String>()
    val creditPaymentListLiveData = Transformations.map(
        combinedNotNullLiveData(creditAmountLiveData, creditRateLiveData, creditTermLiveData)
    ) { (creditAmount: String, creditRate: String, creditTerm: String) -> getCreditPaymentListForAnnuity(creditAmount, creditRate, creditTerm) }

    private fun getCreditPaymentListForAnnuity(creditAmount: String, creditRate: String, creditTerm: String): List<CreditPaymentEntity> {
        val creditAmountValue = creditAmount.toDoubleOrNull() ?: return emptyList()
        val creditRateValue = creditRate.toDoubleOrNull() ?: return emptyList()
        val creditTermValue = creditTerm.toIntOrNull() ?: return emptyList()
        return getPaymentScheduleUseCase.getCreditPaymentListForAnnuity(creditAmountValue, creditRateValue, creditTermValue)
    }

}