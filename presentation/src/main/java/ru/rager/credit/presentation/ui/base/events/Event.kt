package ru.rager.credit.presentation.ui.base.events

sealed class Event {

    object CalculationError : Event()

    object UnknownError : Event()

}