package ru.rager.credit.presentation.util.binding.adapters

import android.widget.ArrayAdapter
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import ru.rager.credit.presentation.view.MaterialSpinner

object MaterialSpinnerAdapter {

    @BindingAdapter("app:array", "app:arrayResources", requireAll = false)
    @JvmStatic
    fun setArray(materialSpinner: MaterialSpinner, array: List<Any>?, arrayResources: List<Int>?) {
        val resources = materialSpinner.resources
        val adapter = when {
            array != null -> ArrayAdapter(materialSpinner.context, android.R.layout.simple_spinner_item, array)
            arrayResources != null -> ArrayAdapter(materialSpinner.context, android.R.layout.simple_spinner_item, arrayResources.map(resources::getString))
            else -> null
        }
        materialSpinner.setAdapter(adapter)
    }

    @BindingAdapter("app:arrayResources")
    @JvmStatic
    fun setArray(materialSpinner: MaterialSpinner, array: List<Int>) {
        materialSpinner.setAdapter(ArrayAdapter(materialSpinner.context, android.R.layout.simple_spinner_item, array))
    }

    @BindingAdapter("app:selectedItem")
    @JvmStatic
    fun setSelectedItem(materialSpinner: MaterialSpinner, position: Int) {
        if (materialSpinner.getSelected() != position) {
            materialSpinner.setSelected(position)
        }
    }

    @InverseBindingAdapter(attribute = "app:selectedItem", event = "app:onItemSelectedChanged")
    @JvmStatic
    fun getSelectedItem(materialSpinner: MaterialSpinner): Int {
        return materialSpinner.getSelected()
    }

    @BindingAdapter("app:onItemSelectedChanged")
    @JvmStatic
    fun setOnItemSelectedListener(
        materialSpinner: MaterialSpinner,
        inverseBindingListener: InverseBindingListener?
    ) {
        materialSpinner.setOnSelectedItemListener(object : MaterialSpinner.OnItemSelectedListener {
            override fun onItemSelected(position: Int) {
                inverseBindingListener?.onChange()
            }
        })
    }

}