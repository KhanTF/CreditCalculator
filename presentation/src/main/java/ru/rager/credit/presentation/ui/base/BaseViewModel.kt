package ru.rager.credit.presentation.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import ru.rager.credit.presentation.util.livedata.SingleLiveEvent

abstract class BaseViewModel(private val router: Router) : ViewModel() {

    private val eventMutableLiveData = SingleLiveEvent<Event>()

    val eventLiveData: LiveData<Event> = eventMutableLiveData

    open fun onBack() {
        router.exit()
    }

    fun postEvent(event: Event) {
        eventMutableLiveData.postValue(event)
    }

    interface Event

}