package ru.rager.credit

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class CreditApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerCreditComponent
            .builder()
            .application(this)
            .build()

}