package ru.rager.credit.presentation.ui.creditcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.rager.credit.domain.entity.CreditEarlyPaymentEntity
import ru.rager.credit.domain.entity.CreditParametersEntity
import ru.rager.credit.domain.entity.CreditRateChangesEntity
import ru.rager.credit.domain.entity.enums.CreditPeriodType
import ru.rager.credit.domain.entity.enums.CreditRateType
import ru.rager.credit.domain.entity.enums.TermTemplateType
import ru.rager.credit.presentation.common.recyclerview.TermTemplateListAdapter
import ru.rager.credit.presentation.dto.CreditParametersParcelable
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.ui.base.ResultNavDirections
import ru.rager.credit.presentation.ui.base.ViewModelEvent
import ru.rager.credit.presentation.ui.base.ViewModelResult
import ru.rager.credit.presentation.ui.createearlypayment.CreditEarlyPaymentResult
import ru.rager.credit.presentation.ui.dialog.datepicker.DatePickerDialogResult
import ru.rager.credit.presentation.util.livedata.combinedLiveData
import ru.rager.credit.presentation.util.livedata.combinedNotNullLiveData
import ru.rager.credit.presentation.util.livedata.mapImmediate
import java.util.*
import javax.inject.Inject

class CreditCalculatorViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        private const val REQUEST_KEY_CREDIT_START_DATE_PICKER = "ru.rager.credit.presentation.ui.creditcalculator.CreditCalculatorViewModel.REQUEST_KEY_CREDIT_START_DATE_PICKER"
        private const val REQUEST_KEY_CREATE_EARLY_PAYMENT = "ru.rager.credit.presentation.ui.creditcalculator.CreditCalculatorViewModel.REQUEST_KEY_CREATE_EARLY_PAYMENT"
    }

    val creditStart: MutableLiveData<Calendar> = MutableLiveData(Calendar.getInstance())
    val creditSum: MutableLiveData<String> = MutableLiveData()
    val isCreditSumInvalid: LiveData<Boolean> = creditSum.mapImmediate(this::isCreditSumInvalid)
    val creditRate: MutableLiveData<String> = MutableLiveData()
    val isCreditRateInvalid: LiveData<Boolean> = creditRate.mapImmediate(this::isCreditRateInvalid)
    val creditTerm: MutableLiveData<String> = MutableLiveData()
    val isCreditTermInvalid: LiveData<Boolean> = creditTerm.mapImmediate(this::isCreditTermInvalid)
    val creditRateTypeSelected: MutableLiveData<Int> = MutableLiveData()
    val creditRateTypeList: LiveData<List<CreditRateType>> = MutableLiveData(CreditRateType.values().toList())
    val creditRateType: LiveData<CreditRateType> =
        combinedNotNullLiveData(creditRateTypeList, creditRateTypeSelected).mapImmediate { (creditRateTypeList: List<CreditRateType>, creditRateTypeSelected: Int) ->
            creditRateTypeList[creditRateTypeSelected]
        }
    val creditRatePeriodSelected: MutableLiveData<Int> = MutableLiveData()
    val creditRatePeriodList: LiveData<List<CreditPeriodType>> = MutableLiveData(getCreditRatePeriodList())
    val creditRatePeriod: LiveData<CreditPeriodType> =
        combinedNotNullLiveData(creditRatePeriodList, creditRatePeriodSelected).mapImmediate { (creditRatePeriodList: List<CreditPeriodType>, creditRatePeriodSelected: Int) ->
            creditRatePeriodList[creditRatePeriodSelected]
        }
    val creditPaymentPeriodSelected: MutableLiveData<Int> = MutableLiveData()
    val creditPaymentPeriodList = MutableLiveData(getCreditPaymentPeriodList())
    val creditPaymentPeriod: LiveData<CreditPeriodType> =
        combinedNotNullLiveData(creditPaymentPeriodList, creditPaymentPeriodSelected).mapImmediate { (creditPaymentPeriodList: List<CreditPeriodType>, creditPaymentPeriodSelected: Int) ->
            creditPaymentPeriodList[creditPaymentPeriodSelected]
        }
    val isCalculateAvailable = combinedLiveData(
        creditSum,
        creditRate,
        creditTerm,
        creditRateType,
        creditRatePeriod,
        creditPaymentPeriod
    ).mapImmediate { (creditSum: String?, creditRate: String?, creditTerm: String?, creditRateType: CreditRateType?, creditRatePeriod: CreditPeriodType?, creditPaymentPeriod: CreditPeriodType?) ->
        getIsCalculationAvailable(creditSum, creditRate, creditTerm, creditRateType, creditRatePeriod, creditPaymentPeriod)
    }
    val creditTermTemplateList = MutableLiveData(TermTemplateType.values().toList())
    val creditEarlyPaymentList = MutableLiveData<List<CreditEarlyPaymentEntity>>(emptyList())
    val creditRateChangesList = MutableLiveData<List<CreditRateChangesEntity>>(emptyList())

    fun onCalculate() {
        val creditStart = creditStart.value ?: return
        val creditRateType = creditRateType.value ?: return
        val creditRatePeriodType = creditRatePeriod.value ?: return
        val creditPaymentPeriod = creditPaymentPeriod.value ?: return
        val creditSum = creditSum.value?.toDoubleOrNull() ?: return
        val creditRate = creditRate.value?.toDoubleOrNull() ?: return
        val creditTerm = creditTerm.value?.toIntOrNull() ?: return
        val parameters = CreditParametersEntity.Builder()
            .setCreditStart(creditStart)
            .setCreditSum(creditSum)
            .setCreditRate(creditRate)
            .setCreditRateType(creditRateType)
            .setCreditTerm(creditTerm)
            .setCreditPaymentPeriod(creditPaymentPeriod)
            .setCreditRatePeriod(creditRatePeriodType)
            .build()
        postNavigationEvent(CreditCalculatorFragmentDirections.toCalculationFragment(CreditParametersParcelable(parameters)))
    }

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

    fun onCreateEarlyPayment() {
        postNavigationEvent(
            ResultNavDirections(
                REQUEST_KEY_CREATE_EARLY_PAYMENT,
                CreditCalculatorFragmentDirections.toCreateEarlyPaymentFragment()
            )
        )
    }

    fun onEarlyPaymentCreated(earlyPaymentEntity: CreditEarlyPaymentEntity) {
        val earlyPaymentList = creditEarlyPaymentList.value.orEmpty().toMutableList()
        earlyPaymentList.add(earlyPaymentEntity)
        creditEarlyPaymentList.value = earlyPaymentList
    }

    fun onDeleteEarlyPayment(index: Int) {
        val earlyPaymentList = creditEarlyPaymentList.value.orEmpty().toMutableList()
        if (index >= 0 && index < earlyPaymentList.size) {
            earlyPaymentList.removeAt(index)
            creditEarlyPaymentList.value = earlyPaymentList
        }
    }

    fun onSelectTermTemplate(value: TermTemplateType) {
        creditTerm.value = (value.value * value.period.value).toString()
    }

    override fun onHandleResult(requestKey: String, payload: ViewModelResult) {
        super.onHandleResult(requestKey, payload)
        if (requestKey == REQUEST_KEY_CREDIT_START_DATE_PICKER && payload is DatePickerDialogResult) {
            onCreditStartSelected(payload.date)
        }
        if (requestKey == REQUEST_KEY_CREATE_EARLY_PAYMENT && payload is CreditEarlyPaymentResult) {
            onEarlyPaymentCreated(payload.creditEarlyPaymentParcelable)
        }
    }


    private fun isCreditSumInvalid(data: String): Boolean {
        if (data.isBlank()) return false
        val value = data.toDoubleOrNull() ?: return true
        return value <= 0 || value >= Double.MAX_VALUE
    }

    private fun isCreditRateInvalid(data: String): Boolean {
        if (data.isBlank()) return false
        val value = data.toDoubleOrNull() ?: return true
        return value <= 0 || value >= 100
    }

    private fun isCreditTermInvalid(data: String): Boolean {
        if (data.isBlank()) return false
        val value = data.toIntOrNull() ?: return true
        return value <= 0 || value > 600
    }

    private fun getCreditRatePeriodList() = listOf(
        CreditPeriodType.EVERY_YEAR,
        CreditPeriodType.EVERY_QUARTER,
        CreditPeriodType.EVERY_MONTH,
    )

    private fun getCreditPaymentPeriodList() = listOf(
        CreditPeriodType.EVERY_MONTH,
        CreditPeriodType.EVERY_QUARTER,
        CreditPeriodType.EVERY_YEAR,
    )

    private fun getIsCalculationAvailable(
        creditSum: String?,
        creditRate: String?,
        creditTerm: String?,
        creditRateType: CreditRateType?,
        creditRatePeriod: CreditPeriodType?,
        creditPaymentPeriod: CreditPeriodType?
    ): Boolean {
        return !isCreditSumInvalid(creditSum ?: return false)
                && !isCreditRateInvalid(creditRate ?: return false)
                && !isCreditTermInvalid(creditTerm ?: return false)
                && creditRateType != null
                && creditRatePeriod != null
                && creditPaymentPeriod != null
    }

}