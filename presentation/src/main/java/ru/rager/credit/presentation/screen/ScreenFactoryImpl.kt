package ru.rager.credit.presentation.screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.rager.credit.presentation.ui.calculation.CalculationFragment
import ru.rager.credit.presentation.ui.calculation.CalculationViewModel
import ru.rager.credit.presentation.ui.main.MainFragment
import ru.rager.credit.presentation.ui.paymentcalculator.PaymentCalculatorFragment
import ru.rager.credit.presentation.ui.percentcalculator.PercentCalculatorFragment

class ScreenFactoryImpl : ScreenFactory {

    override fun getMainScreen(): Screen = getScreen { MainFragment.getInstance() }

    override fun getPaymentCalculatorScreen(): Screen = getScreen { PaymentCalculatorFragment.getInstance() }

    override fun getPercentCalculatorScreen(): Screen = getScreen { PercentCalculatorFragment.getInstance() }

    override fun getCalculationScreen(parameters: CalculationViewModel.Parameters): Screen = getScreen {
        CalculationFragment.getInstance(parameters)
    }

    override fun getCalculationListScreen(): Screen = getScreen { throw Exception() }

    private inline fun getScreen(crossinline provider: () -> Fragment): Screen {
        return object : FragmentScreen {
            override fun createFragment(factory: FragmentFactory): Fragment {
                return provider()
            }
        }
    }

}