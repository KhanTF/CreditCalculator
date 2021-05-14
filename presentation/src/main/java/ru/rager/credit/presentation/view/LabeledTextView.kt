package ru.rager.credit.presentation.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_labeled_text_view.view.*
import ru.rager.credit.presentation.R

class LabeledTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.labeledTextView
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
        inflate(context, R.layout.view_labeled_text_view, this)
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabeledTextView, defStyleAttr, 0)
            text_view.text = typedArray.getText(R.styleable.LabeledTextView_android_text)
            label_text_view.text = typedArray.getText(R.styleable.LabeledTextView_label)
            val textAppearance = typedArray.getResourceId(R.styleable.LabeledTextView_android_textAppearance, 0)
            if (textAppearance != 0) {
                text_view.setTextAppearance(context, textAppearance)
            }
            val titleTextAppearance = typedArray.getResourceId(R.styleable.LabeledTextView_labelTextAppearance, 0)
            if (titleTextAppearance != 0) {
                label_text_view.setTextAppearance(context, titleTextAppearance)
            }
            text_view.setTextColor(typedArray.getColor(R.styleable.LabeledTextView_android_textColor, Color.BLACK))
            label_text_view.setTextColor(typedArray.getColor(R.styleable.LabeledTextView_labelTextColor, Color.BLACK))
            typedArray.recycle()
        }
    }

    fun setLabel(label: String) {
        label_text_view.text = label
    }

    fun getLabel(): String {
        return label_text_view.text.toString()
    }

    fun setText(text: String) {
        text_view.text = text
    }

    fun getText(): String {
        return text_view.text.toString()
    }

}