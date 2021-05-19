package ru.rager.credit.presentation.ui.calculationlist

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.domain.entity.SavedCreditCalculationParameterEntity
import ru.rager.credit.domain.usecase.CalculationParameterUseCase
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class CalculationListViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory,
    private val calculationParameterUseCase: CalculationParameterUseCase
) : BaseViewModel(router) {

    val calculationListLiveData = MutableLiveData<List<SavedCreditCalculationParameterEntity>>()

    init {
        calculationParameterUseCase
            .getAll()
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                calculationListLiveData.value = it
            }, {
                it.printStackTrace()
            })
            .disposeOnClear()
    }

    fun onOpenCreditCalculation(savedCreditCalculationParameterEntity: SavedCreditCalculationParameterEntity) {
        router.navigateTo(screenFactory.getCalculationScreen(savedCreditCalculationParameterEntity))
    }

}