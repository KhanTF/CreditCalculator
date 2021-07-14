package ru.rager.credit.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import ru.rager.credit.presentation.model.MainMenuModel
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.util.livedata.SingleLiveData
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    val mainMenuList = MutableLiveData(getMenuList())
    val openMenuList = SingleLiveData<MainMenuModel>()

    fun onOpenMenu(menuModel: MainMenuModel) {
        openMenuList.setValue(menuModel)
    }

    private fun getMenuList() = listOf(
        MainMenuModel.CalculatePaymentMainMenuModel,
        MainMenuModel.CalculatePercentMainMenuModel,
        MainMenuModel.CalculationListMainMenuModel,
        MainMenuModel.SettingsMainMenuModel
    )

}