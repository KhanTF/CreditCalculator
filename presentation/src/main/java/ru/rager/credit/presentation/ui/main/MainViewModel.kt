package ru.rager.credit.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import ru.rager.credit.presentation.model.MainMenuItem
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory
) : BaseViewModel(router) {

    val mainMenuListLiveData = MutableLiveData<List<MainMenuItem>>()

    init {
        mainMenuListLiveData.value = listOf(
            MainMenuItem.CalculatePaymentMainMenuItem,
            MainMenuItem.CalculatePercentMainMenuItem,
            MainMenuItem.CalculationListMainMenuItem,
            MainMenuItem.SettingsMainMenuItem
        )
    }

    fun onOpenPaymentCalculator() {
        router.navigateTo(screenFactory.getPaymentCalculatorScreen())
    }



}