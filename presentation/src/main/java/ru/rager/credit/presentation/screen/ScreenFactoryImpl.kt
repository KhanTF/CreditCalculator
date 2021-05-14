package ru.rager.credit.presentation.screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.rager.credit.presentation.model.CalculationParameters
import ru.rager.credit.presentation.ui.calculationresult.CalculationResultFragment
import ru.rager.credit.presentation.ui.paymentcalculator.PaymentCalculatorFragment
import ru.rager.credit.presentation.ui.main.MainFragment

class ScreenFactoryImpl : ScreenFactory {

    override fun getMainScreen(): Screen = getScreen { MainFragment.getInstance() }

    override fun getPaymentCalculatorScreen(): Screen = getScreen { PaymentCalculatorFragment.getInstance() }

    override fun getCalculationResultScreen(calculationParameters: CalculationParameters): Screen = getScreen { CalculationResultFragment.getInstance(calculationParameters) }

    private inline fun getScreen(crossinline provider: () -> Fragment): Screen {
        return object : FragmentScreen {
            override fun createFragment(factory: FragmentFactory): Fragment {
                return provider()
            }
        }
    }

}