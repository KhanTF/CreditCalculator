package ru.rager.credit.presentation.ui.calculationlist

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.usecase.GetCalculationParameterUseCase
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.ui.base.events.Event
import javax.inject.Inject

class CalculationListViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory,
    getCalculationParameterUseCase: GetCalculationParameterUseCase
) : BaseViewModel(router) {

    val calculationListLiveData = MutableLiveData<List<CreditCalculationParameterEntity.SavedCalculationParameterEntity>>()

    init {
        getCalculationParameterUseCase
            .getAll()
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                calculationListLiveData.value = it
            }, { e ->
                postEvent(Event.UnknownError)
                e.printStackTrace()
            })
            .disposeOnClear()
    }

    fun onOpenCreditCalculation(savedCalculationParameterEntity: CreditCalculationParameterEntity.SavedCalculationParameterEntity) {
        router.navigateTo(
            screenFactory.getCalculationScreen(
                creditRateType = savedCalculationParameterEntity.creditRateType,
                creditSum = savedCalculationParameterEntity.creditSum,
                creditRate = savedCalculationParameterEntity.creditRate,
                creditTerm = savedCalculationParameterEntity.creditTerm
            )
        )
    }

}