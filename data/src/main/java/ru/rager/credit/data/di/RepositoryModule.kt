package ru.rager.credit.data.di

import dagger.Binds
import dagger.Module
import ru.rager.credit.data.repository.CalculationParameterRepositoryImpl
import ru.rager.credit.domain.repository.CalculationParameterRepository

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindCreditCalculationRepository(creditCalculationRepositoryImpl: CalculationParameterRepositoryImpl): CalculationParameterRepository

}