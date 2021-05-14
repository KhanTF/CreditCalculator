package ru.rager.credit

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import ru.rager.credit.data.di.DataModule
import ru.rager.credit.domain.di.DomainModule
import ru.rager.credit.presentation.di.PresentationModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        PresentationModule::class,
        DomainModule::class,
        DataModule::class
    ]
)
interface CreditComponent : AndroidInjector<CreditApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(context: Context): Builder
        fun build(): CreditComponent
    }

}