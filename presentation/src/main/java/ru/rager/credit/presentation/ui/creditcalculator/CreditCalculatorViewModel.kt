package ru.rager.credit.presentation.ui.creditcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.rager.credit.domain.entity.CreditPretermPaymentEntity
import ru.rager.credit.domain.entity.CreditRateChangeEntity
import ru.rager.credit.domain.entity.PeriodValueEntity
import ru.rager.credit.domain.entity.enums.PeriodType
import ru.rager.credit.domain.entity.enums.RateType
import ru.rager.credit.domain.usecase.TemplateInteractor
import ru.rager.credit.presentation.dto.CreditPretermPaymentParcelable
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.ui.base.ResultNavDirections
import ru.rager.credit.presentation.ui.base.ViewModelResult
import ru.rager.credit.presentation.ui.pretermpayment.PretermPaymentResult
import ru.rager.credit.presentation.ui.dialog.datepicker.DatePickerDialogResult
import ru.rager.credit.presentation.util.emptyString
import ru.rager.credit.presentation.util.livedata.combinedLiveData
import ru.rager.credit.presentation.util.livedata.combinedNotNullLiveData
import ru.rager.credit.presentation.util.livedata.mapImmediate
import java.util.*

abstract class CreditCalculatorViewModel(
    private val templateInteractor: TemplateInteractor,
    arguments: CreditCalculatorFragmentArgs
) : BaseViewModel() {

    companion object {
        private const val REQUEST_KEY_CREDIT_START_DATE_PICKER = "ru.rager.credit.presentation.ui.creditcalculator.CreditCalculatorViewModel.REQUEST_KEY_CREDIT_START_DATE_PICKER"
        private const val REQUEST_KEY_CREATE_PRETERM_PAYMENT = "ru.rager.credit.presentation.ui.creditcalculator.CreditCalculatorViewModel.REQUEST_KEY_CREATE_PRETERM_PAYMENT"
        private const val REQUEST_KEY_EDIT_PRETERM_PAYMENT = "ru.rager.credit.presentation.ui.creditcalculator.CreditCalculatorViewModel.REQUEST_KEY_EDIT_PRETERM_PAYMENT."

        private fun getEditPretermPaymentKey(index: Int): String {
            return REQUEST_KEY_EDIT_PRETERM_PAYMENT + index
        }

        private fun isEditPretermPaymentKey(key: String): Boolean {
            return key.startsWith(REQUEST_KEY_EDIT_PRETERM_PAYMENT)
        }

        private fun getEditPretermIndex(key: String): Int {
            return requireNotNull(key.substringAfterLast(".").toIntOrNull()) { "Incorrect request key to edit preterm" }
        }

    }

    val calculatorType = MutableLiveData(arguments.type)

    val creditStart: MutableLiveData<Calendar> = MutableLiveData(Calendar.getInstance())
    val isCreditStartVisible = MutableLiveData(true)

    val creditSum: MutableLiveData<String> = MutableLiveData(emptyString())
    val isCreditSumVisible = MutableLiveData(true)
    val isCreditSumValid: LiveData<Boolean> = creditSum.mapImmediate { it.isBlank() || isCreditSumValid(it) }

    val creditRate: MutableLiveData<String> = MutableLiveData(emptyString())
    val isCreditRateVisible = MutableLiveData(true)
    val isCreditRateValid: LiveData<Boolean> = creditRate.mapImmediate { it.isBlank() || isCreditRateValid(it) }

    val creditTerm: MutableLiveData<String> = MutableLiveData(emptyString())
    val isCreditTermVisible = MutableLiveData(true)
    val isCreditTermValid: LiveData<Boolean> = creditTerm.mapImmediate { it.isBlank() || isCreditTermValid(it) }

    val creditPayment: MutableLiveData<String> = MutableLiveData(emptyString())
    val isCreditPaymentVisible = MutableLiveData(true)
    val isCreditPaymentValid: LiveData<Boolean> = creditPayment.mapImmediate { it.isBlank() || isCreditPaymentValid(it) }

    val rateTypeSelected: MutableLiveData<Int> = MutableLiveData(0)
    val rateTypeList: LiveData<List<RateType>> by lazy { MutableLiveData(getCreditRateTypeList()) }
    val rateType: LiveData<RateType> = combinedNotNullLiveData(
        rateTypeList,
        rateTypeSelected
    ).mapImmediate { (creditRateTypeList: List<RateType>, creditRateTypeSelected: Int) ->
        creditRateTypeList[creditRateTypeSelected]
    }

    val ratePeriodSelected: MutableLiveData<Int> = MutableLiveData(0)
    val ratePeriodList: LiveData<List<PeriodType>> by lazy { MutableLiveData(getCreditRatePeriodList()) }
    val ratePeriod: LiveData<PeriodType> by lazy {
        combinedNotNullLiveData(ratePeriodList, ratePeriodSelected).mapImmediate { (creditRatePeriodList: List<PeriodType>, creditRatePeriodSelected: Int) ->
            creditRatePeriodList[creditRatePeriodSelected]
        }
    }

    val paymentPeriodSelected: MutableLiveData<Int> = MutableLiveData(0)
    val paymentPeriodList: LiveData<List<PeriodType>> by lazy { MutableLiveData(getCreditPaymentPeriodList()) }
    val paymentPeriod: LiveData<PeriodType> by lazy {
        combinedNotNullLiveData(paymentPeriodList, paymentPeriodSelected).mapImmediate { (creditPaymentPeriodList: List<PeriodType>, creditPaymentPeriodSelected: Int) ->
            creditPaymentPeriodList[creditPaymentPeriodSelected]
        }
    }

    val isPretermVisible = MutableLiveData(true)
    val pretermPaymentList = MutableLiveData<List<CreditPretermPaymentEntity>>(emptyList())

    val isRateChangeVisible = MutableLiveData(true)
    val rateChangeList = MutableLiveData<List<CreditRateChangeEntity>>(emptyList())

    val termTemplateList by lazy { MutableLiveData(templateInteractor.getTermTemplates()) }

    val isCalculateAvailable = combinedLiveData(
        creditSum,
        creditRate,
        creditTerm,
        creditPayment,
        rateType,
        ratePeriod,
        paymentPeriod
    ).mapImmediate { isCalculationAvailable() }

    abstract fun onCalculate()

    fun onSelectCreditStart() {
        postNavigationEvent(
            ResultNavDirections(
                REQUEST_KEY_CREDIT_START_DATE_PICKER,
                CreditCalculatorFragmentDirections.toDatePickerDialog(creditStart.value, null, null)
            )
        )
    }

    fun onCreditStartSelected(date: Calendar) {
        creditStart.value = date
    }

    fun onCreatePretermPayment() {
        postNavigationEvent(
            ResultNavDirections(
                REQUEST_KEY_CREATE_PRETERM_PAYMENT,
                CreditCalculatorFragmentDirections.toPretermPaymentFragment(null)
            )
        )
    }

    fun onPretermPaymentCreated(earlyPaymentEntity: CreditPretermPaymentEntity) {
        val pretermPaymentList = pretermPaymentList.value.orEmpty().toMutableList()
        pretermPaymentList.add(earlyPaymentEntity)
        this.pretermPaymentList.value = pretermPaymentList
    }

    fun onEditPretermPayment(index: Int) {
        val pretermPaymentEntity = pretermPaymentList.value.orEmpty().getOrNull(index) ?: return
        postNavigationEvent(
            ResultNavDirections(
                getEditPretermPaymentKey(index),
                CreditCalculatorFragmentDirections.toPretermPaymentFragment(CreditPretermPaymentParcelable(pretermPaymentEntity))
            )
        )
    }

    fun onPretermPaymentEdited(index: Int, creditPretermPaymentEntity: CreditPretermPaymentEntity) {
        val pretermPaymentList = pretermPaymentList.value.orEmpty().toMutableList()
        if (index >= 0 && index < pretermPaymentList.size) {
            pretermPaymentList[index] = creditPretermPaymentEntity
            this.pretermPaymentList.value = pretermPaymentList
        }
    }

    fun onCreateRateChange() {

    }

    fun onRateChangeCreated() {

    }

    fun onDeleteEarlyPayment(index: Int) {
        val earlyPaymentList = pretermPaymentList.value.orEmpty().toMutableList()
        if (index >= 0 && index < earlyPaymentList.size) {
            earlyPaymentList.removeAt(index)
            pretermPaymentList.value = earlyPaymentList
        }
    }

    fun onSelectTermTemplate(value: PeriodValueEntity) {
        creditTerm.value = value.getMonthCount().toString()
    }

    override fun onHandleResult(requestKey: String, payload: ViewModelResult) {
        super.onHandleResult(requestKey, payload)
        if (requestKey == REQUEST_KEY_CREDIT_START_DATE_PICKER && payload is DatePickerDialogResult) {
            onCreditStartSelected(payload.date)
        }
        if (requestKey == REQUEST_KEY_CREATE_PRETERM_PAYMENT && payload is PretermPaymentResult) {
            onPretermPaymentCreated(payload.pretermPayment)
        }
        if (isEditPretermPaymentKey(requestKey) && payload is PretermPaymentResult) {
            val index = getEditPretermIndex(requestKey)
            onPretermPaymentEdited(index, payload.pretermPayment)
        }
    }

    protected abstract fun isCalculationAvailable(): Boolean

    protected open fun isCreditSumValid(data: String): Boolean {
        val value = data.toDoubleOrNull() ?: return false
        return value > 0 && value < Double.MAX_VALUE
    }

    protected open fun isCreditRateValid(data: String): Boolean {
        val value = data.toDoubleOrNull() ?: return false
        return value > 0 && value < 100
    }

    protected open fun isCreditTermValid(data: String): Boolean {
        val value = data.toIntOrNull() ?: return false
        return value > 0 && value < 600
    }

    protected open fun isCreditPaymentValid(data: String): Boolean {
        val value = data.toIntOrNull() ?: return false
        return value > 0
    }

    protected open fun getCreditRateTypeList() = RateType.values().toList()

    protected open fun getCreditRatePeriodList() = listOf(
        PeriodType.YEAR,
        PeriodType.QUARTER,
        PeriodType.MONTH,
    )

    protected open fun getCreditPaymentPeriodList() = listOf(
        PeriodType.MONTH,
        PeriodType.QUARTER,
        PeriodType.YEAR,
    )

}