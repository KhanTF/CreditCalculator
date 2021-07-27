package ru.rager.credit.presentation.util.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

fun <T> LiveData<T>.toImmediate(): LiveData<T> {
    val immediateLiveData = ImmediateMediatorLiveData<T>()
    immediateLiveData.addSource(this) { immediateLiveData.value = it }
    return immediateLiveData
}

fun <X, Y> LiveData<X>.mapImmediate(
    mapFunction: (X) -> Y
): LiveData<Y> {
    return Transformations.map(this, mapFunction).toImmediate()
}

open class ImmediateMediatorLiveData<T>(val isImmediate: Boolean = true) : MediatorLiveData<T>() {

    override fun hasActiveObservers(): Boolean {
        return super.hasActiveObservers() || isImmediate
    }

    override fun hasObservers(): Boolean {
        return super.hasObservers() || isImmediate
    }

}