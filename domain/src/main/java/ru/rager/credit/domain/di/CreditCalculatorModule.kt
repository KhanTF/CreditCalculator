package ru.rager.credit.domain.di

import dagger.Binds
import dagger.Module
import ru.rager.credit.domain.calculator.CreditPaymentCalculator
import ru.rager.credit.domain.calculator.CreditRateCalculator
import ru.rager.credit.domain.calculator.impl.AnnuityCreditPaymentCalculator
import ru.rager.credit.domain.calculator.impl.AnnuityCreditRateCalculator
import ru.rager.credit.domain.calculator.impl.DifferentiatedCreditPaymentCalculator
import ru.rager.credit.domain.calculator.impl.DifferentiatedCreditRateCalculator
import ru.rager.credit.domain.di.qualifiers.AnnuityQualifier
import ru.rager.credit.domain.di.qualifiers.DifferentiatedQualifier

@Module
abstract class CreditCalculatorModule {

    @Binds
    @AnnuityQualifier
    abstract fun bindAnnuityCreditPaymentCalculator(impl: AnnuityCreditPaymentCalculator): CreditPaymentCalculator

    @Binds
    @DifferentiatedQualifier
    abstract fun bindDifferentiatedCreditPaymentCalculator(impl: DifferentiatedCreditPaymentCalculator): CreditPaymentCalculator

    @Binds
    @AnnuityQualifier
    abstract fun bindAnnuityCreditRateCalculator(impl: AnnuityCreditRateCalculator): CreditRateCalculator

    @Binds
    @DifferentiatedQualifier
    abstract fun bindDifferentiatedCreditRateCalculator(impl: DifferentiatedCreditRateCalculator): CreditRateCalculator

}