package ru.rager.credit.presentation.ui.base

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.rager.credit.presentation.util.livedata.SingleLiveData

abstract class BaseViewModel : ViewModel() {

    val viewModelEvent = SingleLiveData<ViewModelEvent>()
    private val clearedCompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        clearedCompositeDisposable.dispose()
        super.onCleared()
    }

    fun onBack() {
        close()
    }

    protected fun close() {
        postViewModelEvent(CloseDirectionEvent())
    }

    protected fun postViewModelResult(result: ViewModelResult) {
        postViewModelEvent(SetResultEvent(result))
    }

    protected fun postNavigationEvent(navDirections: NavDirections) {
        postViewModelEvent(OpenDirectionEvent(navDirections))
    }

    protected fun postViewModelEvent(event: ViewModelEvent) {
        viewModelEvent.setValue(event)
    }

    open fun onHandleResult(requestKey: String, payload: ViewModelResult) {}

    protected fun Disposable.disposeOnClear() {
        clearedCompositeDisposable.add(this)
    }

}