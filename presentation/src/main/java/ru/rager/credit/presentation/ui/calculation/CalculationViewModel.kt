package ru.rager.credit.presentation.ui.calculation

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.usecase.CreditCalculatorInteractor
import ru.rager.credit.presentation.dto.CreditParametersParcelable
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.ui.base.events.Event
import ru.rager.credit.presentation.util.NEGATIVE_DOUBLE
import javax.inject.Inject

class CalculationViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory,
    creditCalculatorInteractor: CreditCalculatorInteractor,
    parameters: Parameters
) : BaseViewModel(router) {

    private val creditParametersEntity = parameters.creditParametersEntity

    val creditCalculationId: Long? = when {
        creditParametersEntity.id < 0 -> null
        else -> creditParametersEntity.id
    }
    val creditCalculationName: String? = when {
        creditParametersEntity.name.isBlank() -> null
        else -> creditParametersEntity.name
    }
    val creditSum: Double = creditParametersEntity.creditSum
    val creditRate: Double = creditParametersEntity.creditRate
    val creditRateType: CreditRateType = creditParametersEntity.creditRateType
    val creditTerm: Int = creditParametersEntity.creditTerm
    val creditPaymentPeriod = creditParametersEntity.creditPaymentPeriod
    val creditRatePeriod = creditParametersEntity.creditRatePeriod
    val creditCalculationListLiveData = MutableLiveData<List<CreditCalculationEntity>>()
    val creditFirstPaymentLiveData = creditCalculationListLiveData.map { calculationList ->
        calculationList
            .find { it is CreditCalculationEntity.SchedulePaymentCreditCalculationEntity }
            ?.let { it as? CreditCalculationEntity.SchedulePaymentCreditCalculationEntity }
            ?.payment ?: 0.0
    }
    val creditLastPaymentLiveData = creditCalculationListLiveData.map { calculationList ->
        calculationList
            .findLast { it is CreditCalculationEntity.SchedulePaymentCreditCalculationEntity }
            ?.let { it as? CreditCalculationEntity.SchedulePaymentCreditCalculationEntity }
            ?.payment ?: 0.0
    }
    val creditSumPaymentsLiveData = creditCalculationListLiveData.map { calculationList ->
        calculationList.sumByDouble {
            when (it) {
                is CreditCalculationEntity.SchedulePaymentCreditCalculationEntity -> it.payment
                is CreditCalculationEntity.EarlyPaymentCreditCalculationEntity -> it.payment
                else -> 0.0
            }
        }
    }
    val creditOverpaymentsLiveData = creditSumPaymentsLiveData.map { sumPayments ->
        sumPayments - creditSum
    }

    init {
        creditCalculatorInteractor
            .getCalculationListSingle(creditParametersEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(Throwable::printStackTrace)
            .subscribe({
                creditCalculationListLiveData.value = it
            }, {
                postEvent(Event.UnknownError)
            })
            .disposeOnClear()
    }

    fun onSaveCalculation(name: String) {
    }

    fun onDelete() {
    }

    @Parcelize
    data class Parameters(val creditParametersEntity: CreditParametersParcelable) : Parcelable {
        constructor(creditParametersEntity: CreditParametersEntity) : this(
            CreditParametersParcelable(creditParametersEntity)
        )
    }

}