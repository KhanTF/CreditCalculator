package ru.rager.credit.presentation.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.widget.*
import kotlinx.android.synthetic.main.view_material_spinner.view.*
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.util.emptyString

class MaterialSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrStyle: Int = R.attr.materialSpinner
) : FrameLayout(context, attrs, defAttrStyle) {

    private val onItemClickListener = AdapterView.OnItemClickListener { _, _, position: Int, _ ->
        selectedPosition = position
        onItemSelectedListener?.onItemSelected(position)
    }
    private var selectedPosition: Int = 0
    private var onItemSelectedListener: OnItemSelectedListener? = null

    init {
        inflate(context, R.layout.view_material_spinner, this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialSpinner, defAttrStyle, 0)
        val hint = typedArray.getText(R.styleable.MaterialSpinner_hint)
        val hintTextColor = typedArray.getColor(R.styleable.MaterialSpinner_hintTextColor, -1)
        val textColor = typedArray.getColor(R.styleable.MaterialSpinner_android_textColor, -1)
        val textAppearance = typedArray.getResourceId(R.styleable.MaterialSpinner_android_textAppearance, -1)
        val drawableStart = typedArray.getDrawable(R.styleable.MaterialSpinner_android_drawableStart)
        val drawablePadding = typedArray.getDimensionPixelOffset(R.styleable.MaterialSpinner_android_drawablePadding, 0)
        val drawableTint = typedArray.getColor(R.styleable.MaterialSpinner_materialDrawableTint, -1)
        typedArray.recycle()

        if (hint != null) {
            auto_complete_text_view_layout.hint = hint
        }
        if (hintTextColor != -1) {
            auto_complete_text_view_layout.hintTextColor = ColorStateList.valueOf(hintTextColor)
        }
        if (textColor != -1) {
            auto_complete_text_view.setTextColor(textColor)
        }
        if (textAppearance != -1) {
            auto_complete_text_view.setTextAppearance(context, textAppearance)
        }
        if (drawableStart != null) {
            drawableStart.colorFilter = PorterDuffColorFilter(drawableTint, PorterDuff.Mode.SRC_IN)
            auto_complete_text_view.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableStart, null, null, null)
            auto_complete_text_view.compoundDrawablePadding = drawablePadding
        }
        auto_complete_text_view.inputType = EditorInfo.TYPE_NULL
        auto_complete_text_view.onItemClickListener = onItemClickListener
    }

    fun setOnSelectedItemListener(onItemSelectedListener: OnItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener
    }

    override fun onSaveInstanceState(): Parcelable {
        val state = SavedState(super.onSaveInstanceState())
        state.selectedPosition = selectedPosition
        return state
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            setSelected(state.selectedPosition)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    fun setSelected(position: Int) {
        val adapter = auto_complete_text_view.adapter
        val selectedItem = when {
            adapter != null -> adapter.getItem(position).toString()
            else -> emptyString()
        }
        auto_complete_text_view.setText(selectedItem, false)
        selectedPosition = position
        onItemSelectedListener?.onItemSelected(position)
    }

    fun getSelected(): Int {
        return selectedPosition
    }

    fun <T> setAdapter(adapter: T) where T : ListAdapter?, T : Filterable? {
        auto_complete_text_view.setAdapter(adapter)
        if (adapter != null && selectedPosition < adapter.count) {
            setSelected(selectedPosition)
        } else {
            setSelected(0)
        }
    }

    fun getAdapter(): ListAdapter {
        return auto_complete_text_view.adapter
    }

    interface OnItemSelectedListener {
        fun onItemSelected(position: Int)
    }

    class SavedState : BaseSavedState {

        var selectedPosition: Int = 0

        constructor(parcelable: Parcelable?) : super(parcelable)

        constructor(parcel: Parcel) : super(parcel) {
            selectedPosition = parcel.readInt()
        }

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeInt(selectedPosition)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }

    }

}