package ru.rager.credit.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import ru.rager.credit.presentation.model.MainMenuModel
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory
) : BaseViewModel(router) {

    val mainMenuListLiveData = MutableLiveData<List<MainMenuModel>>()

    init {
        mainMenuListLiveData.value = listOf(
            MainMenuModel.CalculatePaymentMainMenuModel,
            MainMenuModel.CalculatePercentMainMenuModel,
            MainMenuModel.CalculationListMainMenuModel,
            MainMenuModel.SettingsMainMenuModel
        )
    }

    fun onOpenPaymentCalculator() {
        router.navigateTo(screenFactory.getPaymentCalculatorScreen())
    }

    fun onOpenPercentCalculator() {
        router.navigateTo(screenFactory.getPercentCalculatorScreen())
    }

    fun onOpenSavedCalculations() {
        router.navigateTo(screenFactory.getCalculationListScreen())
    }

}