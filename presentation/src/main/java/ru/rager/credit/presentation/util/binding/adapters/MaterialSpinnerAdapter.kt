package ru.rager.credit.presentation.util.binding.adapters

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import ru.rager.credit.presentation.view.MaterialSpinner

object MaterialSpinnerAdapter {

    @BindingAdapter("selectedItem")
    @JvmStatic
    fun setSelectedItem(materialSpinner: MaterialSpinner, position: Int) {
        if (materialSpinner.getSelected() != position) {
            materialSpinner.setSelected(position)
        }
    }

    @InverseBindingAdapter(attribute = "selectedItem", event = "onItemSelectedChanged")
    @JvmStatic
    fun getSelectedItem(materialSpinner: MaterialSpinner): Int {
        return materialSpinner.getSelected()
    }

    @BindingAdapter("onItemSelectedChanged")
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