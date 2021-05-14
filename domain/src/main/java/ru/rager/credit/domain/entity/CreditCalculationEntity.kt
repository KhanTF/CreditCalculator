package ru.rager.credit.domain.entity

import ru.rager.credit.domain.entity.enums.CreditCalculationType

data class CreditCalculationEntity(
    val creditCalculationType: CreditCalculationType,
    val creditSum: Double,
    val creditRate: Double,
    val creditTerm: Int,
    val creditCalculationPaymentList: List<CreditCalculationPaymentEntity>
) {

    fun getSumPayments() = creditCalculationPaymentList.sumByDouble { it.creditPayment }

    fun isAnnuity() = creditCalculationType == CreditCalculationType.ANNUITY

    fun isDifferentiated() = creditCalculationType == CreditCalculationType.DIFFERENTIATED

}