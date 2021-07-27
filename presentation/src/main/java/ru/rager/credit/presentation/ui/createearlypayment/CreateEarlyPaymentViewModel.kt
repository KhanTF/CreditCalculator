package ru.rager.credit.presentation.ui.createearlypayment

import androidx.lifecycle.MutableLiveData
import ru.rager.credit.domain.entity.CreditEarlyPaymentEntity
import ru.rager.credit.domain.entity.enums.EarlyPaymentPeriodType
import ru.rager.credit.domain.entity.enums.EarlyPaymentType
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.ui.base.ResultNavDirections
import ru.rager.credit.presentation.ui.base.ViewModelResult
import ru.rager.credit.presentation.ui.dialog.datepicker.DatePickerDialogResult
import ru.rager.credit.presentation.util.livedata.combinedNotNullLiveData
import ru.rager.credit.presentation.util.livedata.mapImmediate
import java.util.*
import javax.inject.Inject

class CreateEarlyPaymentViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        private const val REQUEST_KEY_SELECT_START_DATE = "ru.rager.credit.presentation.ui.createearlypayment.CreateEarlyPaymentViewModel.REQUEST_KEY_SELECT_START_DATE"
        private const val REQUEST_KEY_SELECT_END_DATE = "ru.rager.credit.presentation.ui.createearlypayment.CreateEarlyPaymentViewModel.REQUEST_KEY_SELECT_END_DATE"
    }

    val earlyPaymentTypeList = MutableLiveData(getEarlyPaymentTypeList())
    val earlyPaymentTypeSelected = MutableLiveData<Int>()
    val earlyPaymentType = combinedNotNullLiveData(earlyPaymentTypeList, earlyPaymentTypeSelected).mapImmediate { (list: List<EarlyPaymentType>, selected: Int) ->
        list[selected]
    }
    val earlyPaymentPeriodTypeList = MutableLiveData(getEarlyPaymentPeriodTypeList())
    val earlyPaymentPeriodTypeSelected = MutableLiveData<Int>()
    val earlyPaymentPeriodType = combinedNotNullLiveData(earlyPaymentPeriodTypeList, earlyPaymentPeriodTypeSelected).mapImmediate { (list: List<EarlyPaymentPeriodType>, selected: Int) ->
        list[selected]
    }
    val earlyPaymentStart = MutableLiveData<Calendar>()
    val earlyPaymentEnd = MutableLiveData<Calendar>()
    val earlyPaymentEndForAllTerm = MutableLiveData(false)
    val earlyPaymentSum = MutableLiveData<String>()
    val isRegular = earlyPaymentPeriodType.mapImmediate { it != EarlyPaymentPeriodType.SINGLE }

    fun onSelectEarlyPaymentStart() {
        postNavigationEvent(
            ResultNavDirections(
                REQUEST_KEY_SELECT_START_DATE,
                CreateEarlyPaymentFragmentDirections.toDatePickerDialog(null, null, null)
            )
        )
    }

    fun onSelectEarlyPaymentEnd() {
        postNavigationEvent(
            ResultNavDirections(
                REQUEST_KEY_SELECT_END_DATE,
                CreateEarlyPaymentFragmentDirections.toDatePickerDialog(null, null, null)
            )
        )
    }

    fun onAdd() {
        val earlyPaymentType = earlyPaymentType.value ?: return
        val earlyPaymentPeriodType = earlyPaymentPeriodType.value ?: return
        val earlyPaymentSum = earlyPaymentSum.value?.toDoubleOrNull() ?: return
        val earlyPaymentDate = earlyPaymentStart.value ?: return
        val earlyPaymentEndForAllTerm = earlyPaymentEndForAllTerm.value ?: return
        val earlyPaymentEndDate = when {
            earlyPaymentPeriodType == EarlyPaymentPeriodType.SINGLE -> null
            earlyPaymentEndForAllTerm -> null
            else -> earlyPaymentEnd.value ?: return
        }
        val earlyPayment = CreditEarlyPaymentEntity(
            earlyPaymentType,
            earlyPaymentPeriodType,
            earlyPaymentSum,
            earlyPaymentDate,
            earlyPaymentEndDate
        )
        postViewModelResult(CreditEarlyPaymentResult(earlyPayment))
        close()
    }

    override fun onHandleResult(requestKey: String, payload: ViewModelResult) {
        if (payload is DatePickerDialogResult) {
            when (requestKey) {
                REQUEST_KEY_SELECT_START_DATE -> earlyPaymentStart.value = payload.date
                REQUEST_KEY_SELECT_END_DATE -> earlyPaymentEnd.value = payload.date
            }
        }
    }

    private fun getEarlyPaymentTypeList() = listOf(
        EarlyPaymentType.EARLY_DECREASE_PAYMENT,
        EarlyPaymentType.EARLY_DECREASE_TERM
    )

    private fun getEarlyPaymentPeriodTypeList() = listOf(
        EarlyPaymentPeriodType.SINGLE,
        EarlyPaymentPeriodType.EVERY_MONTH,
        EarlyPaymentPeriodType.EVERY_QUARTER,
        EarlyPaymentPeriodType.EVERY_HALF_YEAR,
        EarlyPaymentPeriodType.EVERY_YEAR,
    )


}