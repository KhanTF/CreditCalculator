package ru.rager.credit.data.di

import dagger.Binds
import dagger.Module
import ru.rager.credit.data.repository.CreditCalculationRepositoryImpl
import ru.rager.credit.domain.repository.CreditCalculationRepository

@Module
abstract class RepositoryBindModule {

    @Binds
    abstract fun bindCreditCalculationRepository(creditCalculationRepositoryImpl: CreditCalculationRepositoryImpl): CreditCalculationRepository

}