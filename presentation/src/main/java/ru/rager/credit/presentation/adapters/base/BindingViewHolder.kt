package ru.rager.credit.presentation.adapters.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingViewHolder<ViewBinding : ViewDataBinding>(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)