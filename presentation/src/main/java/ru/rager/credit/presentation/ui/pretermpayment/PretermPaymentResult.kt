package ru.rager.credit.presentation.ui.pretermpayment

import kotlinx.android.parcel.Parcelize
import ru.rager.credit.presentation.dto.CreditPretermPaymentParcelable
import ru.rager.credit.presentation.ui.base.ViewModelResult

@Parcelize
data class PretermPaymentResult(val pretermPayment: CreditPretermPaymentParcelable) : ViewModelResult()