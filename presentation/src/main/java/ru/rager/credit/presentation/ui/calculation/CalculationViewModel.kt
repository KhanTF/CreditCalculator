package ru.rager.credit.presentation.ui.calculation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.rager.credit.domain.entity.CalculationPaymentEntity
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.usecase.GetPaymentListUseCase
import ru.rager.credit.domain.usecase.SaveCalculationParameterUseCase
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.ui.base.events.Event
import ru.rager.credit.presentation.util.NEGATIVE_DOUBLE
import javax.inject.Inject
import javax.inject.Named

class CalculationViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory,
    private val saveCalculationParameterUseCase: SaveCalculationParameterUseCase,
    private val getPaymentListUseCase: GetPaymentListUseCase,
    @Named("creditCalculationId") val creditCalculationId: Long?,
    @Named("creditCalculationName") val creditCalculationName: String?,
    @Named("creditRateType") val creditRateType: CreditRateType,
    @Named("creditSum") val creditSum: Double,
    @Named("creditRate") val creditRate: Double,
    @Named("creditTerm") val creditTerm: Int
) : BaseViewModel(router) {

    val creditPaymentListLiveData = MutableLiveData<List<CalculationPaymentEntity>>()
    val creditSumPaymentsLiveData = creditPaymentListLiveData.map { calculationPaymentList ->
        calculationPaymentList.sumByDouble { it.creditPayment }

    }
    val creditOverpaymentsLiveData = creditSumPaymentsLiveData.map { creditSumPayments ->
        creditSumPayments - creditSum
    }
    val creditMonthPaymentLiveData = creditPaymentListLiveData.map { calculationPaymentList ->
        when (creditRateType) {
            CreditRateType.ANNUITY -> calculationPaymentList.firstOrNull()?.creditPayment ?: NEGATIVE_DOUBLE
            else -> NEGATIVE_DOUBLE
        }
    }
    val creditFirstPaymentLiveData = creditPaymentListLiveData.map { calculationPaymentList ->
        when (creditRateType) {
            CreditRateType.DIFFERENTIATED -> calculationPaymentList.firstOrNull()?.creditPayment ?: NEGATIVE_DOUBLE
            else -> NEGATIVE_DOUBLE
        }
    }
    val creditLastPaymentLiveData = creditPaymentListLiveData.map { calculationPaymentList ->
        when (creditRateType) {
            CreditRateType.DIFFERENTIATED -> calculationPaymentList.lastOrNull()?.creditPayment ?: NEGATIVE_DOUBLE
            else -> NEGATIVE_DOUBLE
        }
    }

    init {
        getPaymentListUseCase
            .getPaymentListSingle(creditRateType, creditSum, creditRate, creditTerm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                creditPaymentListLiveData.value = it
            }, {
                postEvent(Event.UnknownError)
                it.printStackTrace()
            })
            .disposeOnClear()
    }

    fun onSaveCalculation(name: String) {
        saveCalculationParameterUseCase
            .save(name, creditRateType, creditSum, creditRate, creditTerm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                router.replaceScreen(
                    screenFactory.getCalculationScreen(
                        it.creditCalculationParameterId,
                        it.creditCalculationParameterName,
                        it.creditRateType,
                        it.creditSum,
                        it.creditRate,
                        it.creditTerm
                    )
                )
            }, {
                postEvent(Event.UnknownError)
                it.printStackTrace()
            })
            .disposeOnClear()
    }

}