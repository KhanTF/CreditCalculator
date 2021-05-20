package ru.rager.credit.presentation.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.rager.credit.presentation.ui.base.events.Event
import ru.rager.credit.presentation.util.livedata.SingleLiveEvent

abstract class BaseViewModel(private val router: Router) : ViewModel() {

    private val eventMutableLiveData = SingleLiveEvent<Event>()
    private val clearedCompositeDisposable = CompositeDisposable()

    val eventLiveData: LiveData<Event> = eventMutableLiveData

    override fun onCleared() {
        clearedCompositeDisposable.dispose()
        super.onCleared()
    }

    open fun onBack() {
        router.exit()
    }

    protected fun postEvent(event: Event) {
        eventMutableLiveData.postValue(event)
    }

    protected fun Disposable.disposeOnClear() {
        clearedCompositeDisposable.add(this)
    }

}