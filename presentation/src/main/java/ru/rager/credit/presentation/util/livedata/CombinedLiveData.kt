package ru.rager.credit.presentation.util.livedata

import androidx.lifecycle.*

fun combinedLiveData(vararg liveDataArray: LiveData<out Any?>) = CombinedLiveData(*liveDataArray)

class CombinedLiveData(
    private val liveDataArray: List<LiveData<out Any?>>
) : ImmediateMediatorLiveData<CombinedData>() {

    constructor(vararg liveDataArray: LiveData<out Any?>) : this(liveDataArray.toList())

    init {
        liveDataArray.forEach { liveData ->
            addSource(liveData) { setCombinedData() }
        }
    }

    private fun setCombinedData() {
        value = getCombinedData()
    }

    private fun getCombinedData(): CombinedData {
        val values = liveDataArray.map { it.value }
        return CombinedData(values)
    }

}

class CombinedData(val values: List<Any?> = emptyList()) {

    inline fun <reified T> elementAt(i: Int): T? {
        if (i < 0 || i >= values.size) {
            throw IllegalArgumentException("Parameter not found at $i")
        }
        return when (val value = values[i]) {
            null -> value
            is T -> value
            else -> throw IllegalArgumentException("Can't cast parameter to ${T::class.simpleName}")
        }
    }

    inline operator fun <reified T> component1(): T? = elementAt(0)

    inline operator fun <reified T> component2(): T? = elementAt(1)

    inline operator fun <reified T> component3(): T? = elementAt(2)

    inline operator fun <reified T> component4(): T? = elementAt(3)

    inline operator fun <reified T> component5(): T? = elementAt(4)

    inline operator fun <reified T> component6(): T? = elementAt(5)

    inline operator fun <reified T> component7(): T? = elementAt(6)

    inline operator fun <reified T> component8(): T? = elementAt(7)

    inline operator fun <reified T> component9(): T? = elementAt(8)

    inline operator fun <reified T> component10(): T? = elementAt(9)

}


