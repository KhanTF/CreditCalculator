package ru.rager.credit.presentation.util.binding.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewAdapter {

    @BindingAdapter("app:adapter")
    fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

}