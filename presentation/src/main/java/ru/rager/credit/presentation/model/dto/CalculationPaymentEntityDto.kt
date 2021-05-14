package ru.rager.credit.presentation.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditCalculationPaymentEntity
import javax.inject.Provider

@Parcelize
data class CalculationPaymentEntityDto(
    private val creditMonthNumber: Int,
    private val creditPayment: Double,
    private val creditPercentPartOfPayment: Double,
    private val creditDebtPartOfPayment: Double,
    private val creditDebtReminder: Double
) : Parcelable, Provider<CreditCalculationPaymentEntity> {

    constructor(creditCalculationPaymentEntity: CreditCalculationPaymentEntity) : this(
        creditMonthNumber = creditCalculationPaymentEntity.creditMonthNumber,
        creditPayment = creditCalculationPaymentEntity.creditPayment,
        creditPercentPartOfPayment = creditCalculationPaymentEntity.creditPercentPartOfPayment,
        creditDebtPartOfPayment = creditCalculationPaymentEntity.creditDebtPartOfPayment,
        creditDebtReminder = creditCalculationPaymentEntity.creditDebtReminder
    )

    override fun get() = CreditCalculationPaymentEntity(
        creditMonthNumber = creditMonthNumber,
        creditPayment = creditPayment,
        creditPercentPartOfPayment = creditPercentPartOfPayment,
        creditDebtPartOfPayment = creditDebtPartOfPayment,
        creditDebtReminder = creditDebtReminder
    )

}