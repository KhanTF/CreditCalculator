package ru.rager.credit.presentation.ui.dialog.simple

import androidx.lifecycle.MutableLiveData
import ru.rager.credit.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class SimpleDialogViewModel @Inject constructor(
    private val arguments: SimpleDialogFragmentArgs
) : BaseViewModel() {

    val title = MutableLiveData(arguments.title)
    val message = MutableLiveData(arguments.message)
    val ok = MutableLiveData(arguments.ok)
    val cancel = MutableLiveData(arguments.cancel)

    fun onConfirm() {
        postViewModelResult(SimpleDialogResult(true))
        close()
    }

    fun onCancel() {
        postViewModelResult(SimpleDialogResult(false))
        close()
    }

}