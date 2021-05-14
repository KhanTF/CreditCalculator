package ru.rager.credit.presentation.util.binding.adapters

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object SpinnerAdapter {

    @BindingAdapter("app:array", "app:arrayResources", requireAll = false)
    @JvmStatic
    fun setArray(spinner: Spinner, array: List<Any>?, arrayResources: List<Int>?) {
        val resources = spinner.resources
        val adapter = when {
            array != null -> ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, array)
            arrayResources != null -> ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, arrayResources.map(resources::getString))
            else -> null
        }
        spinner.adapter = adapter
    }

    @BindingAdapter("app:arrayResources")
    @JvmStatic
    fun setArray(spinner: Spinner, array: List<Int>) {
        spinner.onItemSelectedListener
        spinner.adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, array)
    }

    @BindingAdapter("app:selectedItem")
    @JvmStatic
    fun setSelectedItem(spinner: Spinner, position: Int) {
        if (spinner.selectedItemPosition != position) {
            spinner.setSelection(position)
        }
    }

    @InverseBindingAdapter(attribute = "app:selectedItem", event = "app:onItemSelectedChanged")
    @JvmStatic
    fun getSelectedItem(spinner: Spinner): Int {
        return spinner.selectedItemPosition
    }

    @BindingAdapter("app:onItemSelectedChanged")
    @JvmStatic
    fun setOnItemSelectedListener(
        spinner: Spinner,
        inverseBindingListener: InverseBindingListener?
    ) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                inverseBindingListener?.onChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                inverseBindingListener?.onChange()
            }
        }
    }

}