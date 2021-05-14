package ru.rager.credit.presentation.screen

import com.github.terrakok.cicerone.Screen

interface ScreenFactory {

    fun getMainScreen(): Screen

    fun getPaymentCalculatorScreen(): Screen

}