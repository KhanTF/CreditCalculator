package ru.rager.credit.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.rager.credit.presentation.R

sealed class MainMenuItem(@DrawableRes val icon: Int, @StringRes val title: Int, @StringRes val subtitle: Int) {

    object CalculatePaymentMainMenuItem : MainMenuItem(R.drawable.ic_calculate_payment, R.string.calculate_payment, R.string.calculate_payment_description)

    object CalculatePercentMainMenuItem : MainMenuItem(R.drawable.ic_calculate_percent, R.string.calculate_percent, R.string.calculate_percent_description)

    object CalculationListMainMenuItem : MainMenuItem(R.drawable.ic_saved_calculations, R.string.saved_calculations, R.string.saved_calculations_description)

    object SettingsMainMenuItem : MainMenuItem(R.drawable.ic_settings, R.string.settings, R.string.settings_description)

}