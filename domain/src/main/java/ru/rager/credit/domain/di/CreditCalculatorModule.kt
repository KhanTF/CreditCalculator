package ru.rager.credit.domain.di

import dagger.Binds
import dagger.Module
import ru.rager.credit.domain.calculator.CreditCalculator
import ru.rager.credit.domain.calculator.impl.AnnuityCreditCalculator
import ru.rager.credit.domain.calculator.impl.DifferentiatedCreditCalculator
import ru.rager.credit.domain.di.qualifiers.AnnuityQualifier
import ru.rager.credit.domain.di.qualifiers.DifferentiatedQualifier

@Module
abstract class CreditCalculatorModule {

    @Binds
    @AnnuityQualifier
    abstract fun bindAnnuityCreditCalculator(impl: AnnuityCreditCalculator): CreditCalculator

    @Binds
    @DifferentiatedQualifier
    abstract fun bindDifferentiatedCreditCalculator(impl: DifferentiatedCreditCalculator): CreditCalculator

}