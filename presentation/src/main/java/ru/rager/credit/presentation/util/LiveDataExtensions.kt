package ru.rager.credit.presentation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <I, O> LiveData<I>.map(function: (I) -> O): LiveData<O> {
    return Transformations.map(this, function)
}

fun <I, O> LiveData<I>.switchMap(function: (I) -> LiveData<O>): LiveData<O> {
    return Transformations.switchMap(this, function)
}