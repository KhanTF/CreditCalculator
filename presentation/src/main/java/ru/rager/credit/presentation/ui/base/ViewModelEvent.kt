package ru.rager.credit.presentation.ui.base

import androidx.navigation.NavDirections

sealed class ViewModelEvent {
    abstract class BaseEvent : ViewModelEvent()
    abstract class NavigationEvent : ViewModelEvent()
    abstract class ExtendedEvent : ViewModelEvent()
}

class ErrorEvent(val throwable: Throwable?) : ViewModelEvent.BaseEvent()

class SetResultEvent(val payload: ViewModelResult) : ViewModelEvent.BaseEvent()

class OpenDirectionEvent(val direction: NavDirections) : ViewModelEvent.NavigationEvent()

class CloseDirectionEvent : ViewModelEvent.NavigationEvent()



