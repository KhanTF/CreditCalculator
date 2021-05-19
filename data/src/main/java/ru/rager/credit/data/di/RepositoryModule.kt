package ru.rager.credit.data.di

import dagger.Binds
import dagger.Module
import ru.rager.credit.data.repository.CreditCalculationParameterRepositoryImpl
import ru.rager.credit.domain.repository.CreditCalculationParameterRepository

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindCreditCalculationRepository(creditCalculationRepositoryImpl: CreditCalculationParameterRepositoryImpl): CreditCalculationParameterRepository

}