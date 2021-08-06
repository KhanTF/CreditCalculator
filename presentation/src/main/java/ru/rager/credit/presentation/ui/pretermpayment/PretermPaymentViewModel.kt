package ru.rager.credit.presentation.ui.pretermpayment

import androidx.lifecycle.MutableLiveData
import ru.rager.credit.domain.entity.CreditPretermPaymentEntity
import ru.rager.credit.domain.entity.PeriodValueEntity
import ru.rager.credit.domain.entity.enums.PretermType
import ru.rager.credit.domain.usecase.TemplateInteractor
import ru.rager.credit.presentation.dto.CreditPretermPaymentParcelable
import ru.rager.credit.presentation.ui.base.BaseViewModel
import ru.rager.credit.presentation.ui.base.ResultNavDirections
import ru.rager.credit.presentation.ui.base.ViewModelResult
import ru.rager.credit.presentation.ui.dialog.datepicker.DatePickerDialogResult
import ru.rager.credit.presentation.util.emptyString
import ru.rager.credit.presentation.util.indexOfOr
import ru.rager.credit.presentation.util.livedata.combinedNotNullLiveData
import ru.rager.credit.presentation.util.livedata.mapImmediate
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject

class PretermPaymentViewModel @Inject constructor(
    private val templateInteractor: TemplateInteractor,
    private val arguments: PretermPaymentFragmentArgs
) : BaseViewModel() {

    companion object {
        private const val REQUEST_KEY_SELECT_START_DATE = "ru.rager.credit.presentation.ui.pretermpayment.PretermPaymentViewModel.REQUEST_KEY_SELECT_START_DATE"
        private const val REQUEST_KEY_SELECT_END_DATE = "ru.rager.credit.presentation.ui.pretermpayment.PretermPaymentViewModel.REQUEST_KEY_SELECT_END_DATE"
    }

    val pretermTypeList = MutableLiveData(getPretermTypeList())
    val pretermTypeSelected = MutableLiveData(getDefaultPretermTypeSelected())
    val pretermType = combinedNotNullLiveData(pretermTypeList, pretermTypeSelected).mapImmediate { (list: List<PretermType>, selected: Int) ->
        list[selected]
    }
    val pretermPeriodValueList = MutableLiveData(getPretermPeriodTemplateList())
    val pretermPeriodValueSelected = MutableLiveData(getDefaultPretermPeriodSelected())
    val pretermPeriodValue = combinedNotNullLiveData(pretermPeriodValueList, pretermPeriodValueSelected).mapImmediate { (list: List<PeriodValueEntity>, selected: Int) ->
        list[selected]
    }
    val pretermStart = MutableLiveData(getDefaultStart())
    val pretermEnd = MutableLiveData(getDefaultEnd())
    val pretermIsInfinite = MutableLiveData(isDefaultInfinite())
    val pretermSum = MutableLiveData(getDefaultSum())

    val isRegular = pretermPeriodValue.mapImmediate { it.value > 0 }
    val isEdited = MutableLiveData(arguments.pretermPayment != null)

    fun onSelectPretermStart() {
        postNavigationEvent(
            ResultNavDirections(
                REQUEST_KEY_SELECT_START_DATE,
                PretermPaymentFragmentDirections.toDatePickerDialog(null, null, null)
            )
        )
    }

    fun onSelectPretermEnd() {
        postNavigationEvent(
            ResultNavDirections(
                REQUEST_KEY_SELECT_END_DATE,
                PretermPaymentFragmentDirections.toDatePickerDialog(null, null, null)
            )
        )
    }

    fun onSubmit() {
        val pretermType = pretermType.value ?: return
        val pretermSum = pretermSum.value?.replace(" ", "")?.toDoubleOrNull() ?: return
        val pretermStart = pretermStart.value ?: return
        val pretermIsInfinite = pretermIsInfinite.value ?: return
        val pretermPeriodValue = pretermPeriodValue.value ?: return
        val pretermEnd = when {
            pretermIsInfinite -> null
            pretermPeriodValue.value <= 0 -> null
            else -> pretermEnd.value
        }
        val pretermPayment = CreditPretermPaymentEntity(
            type = pretermType,
            periodValue = pretermPeriodValue,
            paymentSum = pretermSum,
            start = pretermStart,
            end = pretermEnd
        )
        postViewModelResult(PretermPaymentResult(CreditPretermPaymentParcelable(pretermPayment)))
        close()
    }

    override fun onHandleResult(requestKey: String, payload: ViewModelResult) {
        if (payload is DatePickerDialogResult) {
            when (requestKey) {
                REQUEST_KEY_SELECT_START_DATE -> pretermStart.value = payload.date
                REQUEST_KEY_SELECT_END_DATE -> pretermEnd.value = payload.date
            }
        }
    }

    private fun getPretermTypeList() = PretermType.values().toList()

    private fun getDefaultPretermTypeSelected() = PretermType.values().indexOfOr(arguments.pretermPayment?.type, 0)

    private fun getPretermPeriodTemplateList() = templateInteractor.getPretermTemplates()

    private fun getDefaultPretermPeriodSelected(): Int = getPretermPeriodTemplateList().indexOfOr(arguments.pretermPayment?.periodValue, 0)

    private fun getDefaultStart(): Calendar? {
        return arguments.pretermPayment?.start
    }

    private fun getDefaultEnd(): Calendar? {
        val pretermPayment = arguments.pretermPayment
        return when {
            pretermPayment == null || !pretermPayment.isRegular() -> null
            else -> pretermPayment.end
        }
    }

    private fun getDefaultSum(): String {
        val paymentSum = arguments.pretermPayment?.paymentSum
        return if (paymentSum != null) {
            val format = DecimalFormat("###.###")
            format.format(paymentSum)
        } else {
            emptyString()
        }
    }

    private fun isDefaultInfinite(): Boolean {
        val pretermPayment = arguments.pretermPayment
        return pretermPayment?.isRegular() ?: false
    }

}