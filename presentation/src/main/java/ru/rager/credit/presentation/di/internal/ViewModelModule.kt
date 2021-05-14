package ru.rager.credit.presentation.di.internal

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.rager.credit.presentation.ui.RootViewModel
import ru.rager.credit.presentation.ui.paymentcalculator.PaymentCalculatorViewModel
import ru.rager.credit.presentation.ui.main.MainViewModel

@Module
internal class ViewModelModule {

    @Provides
    @IntoMap
    @ClassKey(RootViewModel::class)
    fun provideRootViewModel(rootViewModel: RootViewModel): ViewModel = rootViewModel

    @Provides
    @IntoMap
    @ClassKey(MainViewModel::class)
    fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel = mainViewModel

    @Provides
    @IntoMap
    @ClassKey(PaymentCalculatorViewModel::class)
    fun provideCalculatorViewModel(paymentCalculatorViewModel: PaymentCalculatorViewModel): ViewModel = paymentCalculatorViewModel

}
