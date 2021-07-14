package ru.rager.credit.presentation.ui.base

import android.os.Bundle
import androidx.navigation.NavDirections

class ResultNavDirections(val requestKey: String, private val directions: NavDirections) : NavDirections {

    companion object {
        const val KEY_REQUEST_KEY = "ru.rager.credit.presentation.ui.base.RequestResultNavDirections.KEY_REQUEST_KEY"
    }

    private val argumentsInternal = Bundle()

    override val actionId: Int
        get() = directions.actionId

    override val arguments: Bundle
        get() = argumentsInternal

    init {
        argumentsInternal.putAll(directions.arguments)
        argumentsInternal.putString(KEY_REQUEST_KEY, requestKey)
    }

}