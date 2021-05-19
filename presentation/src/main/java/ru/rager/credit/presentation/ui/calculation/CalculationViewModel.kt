package ru.rager.credit.presentation.ui.calculation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.SavedCreditCalculationParameterEntity
import ru.rager.credit.domain.entity.enums.CreditCalculationType
import ru.rager.credit.domain.usecase.GetCalculationParameterUseCase
import ru.rager.credit.domain.usecase.GetCalculationUseCase
import ru.rager.credit.domain.usecase.CalculationParameterUseCase
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.util.NEGATIVE_DOUBLE
import javax.inject.Inject

class CalculationViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory,
    private val creditCalculationParameterEntity: CreditCalculationParameterEntity,
    private val calculationParameterUseCase: CalculationParameterUseCase,
    getCalculationUseCase: GetCalculationUseCase
) : BaseViewModel(router) {

    private val creditCalculationEntityLiveData = MutableLiveData<CreditCalculationEntity>()
    val creditCalculationName = when (creditCalculationParameterEntity) {
        is SavedCreditCalculationParameterEntity -> creditCalculationParameterEntity.creditCalculationParameterName
        else -> null
    }
    val creditCalculationType = creditCalculationParameterEntity.creditCalculationType
    val creditCalculationIsSaved = creditCalculationParameterEntity is SavedCreditCalculationParameterEntity
    val creditSum = creditCalculationParameterEntity.creditSum
    val creditRate = creditCalculationParameterEntity.creditRate
    val creditTerm = creditCalculationParameterEntity.creditTerm

    val creditPaymentListLiveData = creditCalculationEntityLiveData.map(CreditCalculationEntity::creditCalculationPaymentList)
    val creditSumPaymentsLiveData = creditCalculationEntityLiveData.map(CreditCalculationEntity::getSumPayments)
    val creditOverpaymentsLiveData = creditCalculationEntityLiveData.map(CreditCalculationEntity::getOverPayments)
    val creditMonthPaymentLiveData = creditCalculationEntityLiveData.map { creditCalculationEntity ->
        when (creditCalculationParameterEntity.creditCalculationType) {
            CreditCalculationType.ANNUITY -> creditCalculationEntity.creditCalculationPaymentList.firstOrNull()?.creditPayment ?: NEGATIVE_DOUBLE
            else -> NEGATIVE_DOUBLE
        }
    }
    val creditFirstPaymentLiveData = creditCalculationEntityLiveData.map { creditCalculationEntity ->
        when (creditCalculationParameterEntity.creditCalculationType) {
            CreditCalculationType.DIFFERENTIATED -> creditCalculationEntity.creditCalculationPaymentList.firstOrNull()?.creditPayment ?: NEGATIVE_DOUBLE
            else -> NEGATIVE_DOUBLE
        }
    }
    val creditLastPaymentLiveData = creditCalculationEntityLiveData.map { creditCalculationEntity ->
        when (creditCalculationParameterEntity.creditCalculationType) {
            CreditCalculationType.DIFFERENTIATED -> creditCalculationEntity.creditCalculationPaymentList.lastOrNull()?.creditPayment ?: NEGATIVE_DOUBLE
            else -> NEGATIVE_DOUBLE
        }
    }

    init {
        getCalculationUseCase
            .getCreditCalculationByRate(creditCalculationParameterEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                creditCalculationEntityLiveData.value = it
            }, {
                it.printStackTrace()
            })
            .disposeOnClear()
    }

    fun onSaveCalculation(name: String) {
        calculationParameterUseCase
            .save(name, creditCalculationParameterEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                router.replaceScreen(screenFactory.getCalculationScreen(it))
            }, {
                it.printStackTrace()
            })
            .disposeOnClear()
    }

}