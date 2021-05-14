package ru.rager.credit.presentation.ui

import com.github.terrakok.cicerone.Router
import ru.rager.credit.presentation.screen.ScreenFactory
import ru.rager.credit.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class RootViewModel @Inject constructor(
    private val router: Router,
    private val screenFactory: ScreenFactory
) : BaseViewModel(router) {

    fun onOpenMain() {
        router.newRootScreen(screenFactory.getMainScreen())
    }

}