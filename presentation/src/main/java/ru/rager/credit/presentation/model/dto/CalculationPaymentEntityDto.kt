package ru.rager.credit.presentation.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CalculationPaymentEntity
import javax.inject.Provider

@Parcelize
data class CalculationPaymentEntityDto(
    private val creditMonthNumber: Int,
    private val creditPayment: Double,
    private val creditPercentPartOfPayment: Double,
    private val creditDebtPartOfPayment: Double,
    private val creditDebtReminder: Double
) : Parcelable, Provider<CalculationPaymentEntity> {

    constructor(calculationPaymentEntity: CalculationPaymentEntity) : this(
        creditMonthNumber = calculationPaymentEntity.creditPaymentOrder,
        creditPayment = calculationPaymentEntity.creditPayment,
        creditPercentPartOfPayment = calculationPaymentEntity.creditPercentPartOfPayment,
        creditDebtPartOfPayment = calculationPaymentEntity.creditDebtPartOfPayment,
        creditDebtReminder = calculationPaymentEntity.creditDebtReminder
    )

    override fun get() = CalculationPaymentEntity(
        creditPaymentOrder = creditMonthNumber,
        creditPayment = creditPayment,
        creditPercentPartOfPayment = creditPercentPartOfPayment,
        creditDebtPartOfPayment = creditDebtPartOfPayment,
        creditDebtReminder = creditDebtReminder
    )

}