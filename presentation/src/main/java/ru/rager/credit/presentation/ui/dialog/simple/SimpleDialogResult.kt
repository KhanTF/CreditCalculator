package ru.rager.credit.presentation.ui.dialog.simple

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.rager.credit.presentation.ui.base.ViewModelResult

@Parcelize
data class SimpleDialogResult(val confirmed: Boolean) : ViewModelResult()
