package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.CreditCalculationType

sealed class CreditCalculationEntity constructor(
    val creditCalculationType: CreditCalculationType,
    val creditCalculationParameter: CreditCalculationParameterEntity,
    val creditCalculationPaymentList: List<CreditCalculationPaymentEntity>
) {

    class TempCreditCalculationEntity(
        creditCalculationType: CreditCalculationType,
        creditCalculationParameter: CreditCalculationParameterEntity,
        creditCalculationPaymentList: List<CreditCalculationPaymentEntity>
    ) : CreditCalculationEntity(creditCalculationType, creditCalculationParameter, creditCalculationPaymentList)

    class SavedCreditCalculationEntity(
        val creditCalculationId: Long,
        val creditCalculationName: String,
        creditCalculationType: CreditCalculationType,
        creditCalculationParameter: CreditCalculationParameterEntity,
        creditCalculationPaymentList: List<CreditCalculationPaymentEntity>
    ) : CreditCalculationEntity(creditCalculationType, creditCalculationParameter, creditCalculationPaymentList)

    fun getSumPayments() = creditCalculationPaymentList.sumByDouble { it.creditPayment }

    fun getOverPayments(): Double {
        val overPayments = getSumPayments() - creditCalculationParameter.creditSum
        return if (overPayments < 0) {
            0.0
        } else {
            overPayments
        }
    }

}
