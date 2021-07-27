package ru.rager.credit.presentation.ui.createearlypayment

import kotlinx.android.parcel.Parcelize
import ru.rager.credit.domain.entity.CreditEarlyPaymentEntity
import ru.rager.credit.presentation.dto.CreditEarlyPaymentParcelable
import ru.rager.credit.presentation.ui.base.ViewModelResult

@Parcelize
data class CreditEarlyPaymentResult(val creditEarlyPaymentParcelable: CreditEarlyPaymentParcelable) : ViewModelResult() {

    constructor(creditEarlyPaymentEntity: CreditEarlyPaymentEntity) : this(CreditEarlyPaymentParcelable(creditEarlyPaymentEntity))

}