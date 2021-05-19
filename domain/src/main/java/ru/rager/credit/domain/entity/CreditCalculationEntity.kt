package ru.rager.credit.domain.entity

data class CreditCalculationEntity constructor(
    val creditCalculationParameter: CreditCalculationParameterEntity,
    val creditCalculationPaymentList: List<CreditCalculationPaymentEntity>
) {

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
