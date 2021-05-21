package ru.rager.credit.presentation.ui.calculation

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CalculationPaymentEntity
import ru.rager.credit.domain.entity.enums.CreditFrequencyType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.usecase.GetPaymentListUseCase
import ru.rager.credit.domain.usecase.RemoveCalculationParameterUseCase
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
    private val removeCalculationParameterUseCase: RemoveCalculationParameterUseCase,
    getPaymentListUseCase: GetPaymentListUseCase,
    parameters: Parameters
) : BaseViewModel(router) {

    val creditCalculationId: Long? = parameters.id
    val creditCalculationName: String? = parameters.name
    val creditRateType: CreditRateType = parameters.creditRateType
    val creditSum: Double = parameters.creditSum
    val creditRate: Double = parameters.creditRate
    val creditTerm: Int = parameters.creditTerm
    val creditPaymentFrequency = parameters.creditPaymentFrequencyType
    val creditRateFrequency = parameters.creditRateFrequencyType
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
            .getPaymentListSingle(
                creditRateType = creditRateType,
                creditSum = creditSum,
                creditRate = creditRate,
                creditTerm = creditTerm,
                creditRateFrequency = creditRateFrequency,
                creditPaymentFrequency = creditPaymentFrequency
            )
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
            .save(
                name = name,
                creditRateType = creditRateType,
                creditSum = creditSum,
                creditRate = creditRate,
                creditTerm = creditTerm,
                creditRateFrequency = creditRateFrequency,
                creditPaymentFrequency = creditPaymentFrequency
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                router.replaceScreen(
                    screenFactory.getCalculationScreen(
                        Parameters(
                            it.creditCalculationParameterId,
                            it.creditCalculationParameterName,
                            it.creditRateType,
                            it.creditSum,
                            it.creditRate,
                            it.creditTerm,
                            it.creditRateFrequency,
                            it.creditPaymentFrequency
                        )
                    )
                )
            }, {
                postEvent(Event.UnknownError)
                it.printStackTrace()
            })
            .disposeOnClear()
    }

    fun onDelete() {
        if (creditCalculationId != null) {
            removeCalculationParameterUseCase
                .remove(creditCalculationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    router.exit()
                }, {
                    postEvent(Event.UnknownError)
                    it.printStackTrace()
                })
                .disposeOnClear()
        }
    }

    @Parcelize
    data class Parameters(
        val id: Long? = null,
        val name: String? = null,
        val creditRateType: CreditRateType,
        val creditSum: Double,
        val creditRate: Double,
        val creditTerm: Int,
        val creditRateFrequencyType: CreditFrequencyType,
        val creditPaymentFrequencyType: CreditFrequencyType
    ) : Parcelable

}