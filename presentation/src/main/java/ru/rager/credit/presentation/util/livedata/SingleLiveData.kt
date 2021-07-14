package ru.rager.credit.presentation.util.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveData<T> : MutableLiveData<T>() {

    private val observerMap = mutableMapOf<Observer<in T>, LifecycleObserver<in T>>()
    private val isPending = AtomicBoolean(false)

    private fun checkObservers() {
        if (hasActiveObservers()) {
            throw IllegalArgumentException("Single live data must hase only one observer")
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        checkObservers()
        val lifecycleObserver = LifecycleObserver(owner, observer)
        observerMap[observer] = lifecycleObserver
        super.observe(owner, lifecycleObserver)
    }

    override fun observeForever(observer: Observer<in T>) {
        checkObservers()
        val lifecycleObserver = LifecycleObserver(null, observer)
        observerMap[observer] = lifecycleObserver
        super.observeForever(lifecycleObserver)
    }

    override fun removeObserver(observer: Observer<in T>) {
        val lifecycleObserver = observerMap[observer] ?: return
        observerMap.remove(observer)
        super.removeObserver(lifecycleObserver)
    }

    override fun removeObservers(owner: LifecycleOwner) {
        observerMap.clear()
        observerMap.putAll(observerMap.filterValues { !it.isAttachedTo(owner) })
        super.removeObservers(owner)
    }

    override fun setValue(value: T) {
        isPending.set(false)
        super.setValue(value)
    }

    override fun postValue(value: T) {
        throw IllegalArgumentException("Operation not supported")
    }

    private inner class LifecycleObserver<K>(
        private val lifecycleOwner: LifecycleOwner?,
        private val observer: Observer<K>
    ) : Observer<K> {

        fun isAttachedTo(lifecycleOwner: LifecycleOwner) = this.lifecycleOwner == lifecycleOwner

        override fun onChanged(value: K) {
            if (isPending.compareAndSet(false, true)) {
                observer.onChanged(value)
            }
        }
    }

}