package ru.rager.credit.presentation.ui.calculationresult

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.domain.entity.CreditCalculationPercentType
import ru.rager.credit.domain.usecase.GetCreditCalculationResultUseCase
import ru.rager.credit.presentation.model.CalculationParameters
import ru.rager.credit.presentation.model.MainMenuItem
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class CalculationResultViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory,
    private val calculationParameters: CalculationParameters,
    private val getCreditCalculationResultUseCase: GetCreditCalculationResultUseCase
) : BaseViewModel(router) {

    val creditCalculationLiveData = MutableLiveData<CreditCalculationEntity>()

    init {
        creditCalculationLiveData.value = when (calculationParameters.creditCalculationPercentType) {
            CreditCalculationPercentType.ANNUITY -> getCreditCalculationResultUseCase.getAnnuityCalculationResult(
                calculationParameters.creditDebtSum,
                calculationParameters.creditPercentRate,
                calculationParameters.creditTerm
            )
            CreditCalculationPercentType.DIFFERENTIATED -> getCreditCalculationResultUseCase.getDifferentiatedCalculationResult(
                calculationParameters.creditDebtSum,
                calculationParameters.creditPercentRate,
                calculationParameters.creditTerm
            )
        }

    }

}