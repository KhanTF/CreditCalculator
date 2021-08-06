package ru.rager.credit.presentation.ui.creditcalculator

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.domain.usecase.CreditCalculatorInteractor
import ru.rager.credit.domain.usecase.TemplateInteractor
import ru.rager.credit.presentation.dto.CreditParametersParcelable
import ru.rager.credit.presentation.ui.base.ErrorEvent
import java.util.*
import javax.inject.Inject

class PercentCreditCalculatorViewModel @Inject constructor(
    private val creditCalculatorInteractor: CreditCalculatorInteractor,
    templateInteractor: TemplateInteractor,
    creditCalculatorFragmentArgs: CreditCalculatorFragmentArgs
) : CreditCalculatorViewModel(templateInteractor, creditCalculatorFragmentArgs) {

    init {
        isCreditStartVisible.value = false
        isCreditRateVisible.value = false
        isPretermVisible.value = false
        isRateChangeVisible.value = false
    }

    override fun onCalculate() {
        val creditRateType = rateType.value ?: return
        val creditRatePeriod = ratePeriod.value ?: return
        val creditPaymentPeriod = paymentPeriod.value ?: return
        val creditSum = creditSum.value?.toDoubleOrNull() ?: return
        val creditTerm = creditTerm.value?.toIntOrNull() ?: return
        val creditPayment = creditPayment.value?.toDoubleOrNull() ?: return
        creditCalculatorInteractor
            .getCreditRateSingle(
                creditSum = creditSum,
                creditPaymentSum = creditPayment,
                creditRateType = creditRateType,
                creditTerm = creditTerm,
                creditRatePeriod = creditRatePeriod,
                creditPaymentPeriod = creditPaymentPeriod
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(Throwable::printStackTrace)
            .subscribe({ creditRate ->
                val parameters = CreditParametersEntity(
                    id = 0L,
                    name = "",
                    creditStart = Calendar.getInstance(),
                    creditSum = creditSum,
                    creditRate = creditRate,
                    creditRateType = creditRateType,
                    creditTerm = creditTerm,
                    creditSkipWeekend = false,
                    creditRatePeriod = creditRatePeriod,
                    creditPaymentPeriod = creditPaymentPeriod,
                    creditPretermPaymentEntityList = emptyList(),
                    creditRateChangeList = emptyList()
                )
                postNavigationEvent(CreditCalculatorFragmentDirections.toCalculationFragment(CreditParametersParcelable(parameters)))
            }, {
                postViewModelEvent(ErrorEvent(it))
            })
            .disposeOnClear()
    }

    override fun isCalculationAvailable(): Boolean {
        return isCreditSumValid(creditSum.value ?: return false)
                && isCreditPaymentValid(creditPayment.value ?: return false)
                && isCreditTermValid(creditTerm.value ?: return false)
                && rateType.value != null
                && ratePeriod.value != null
                && paymentPeriod.value != null
    }

}