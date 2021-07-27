package ru.rager.credit.presentation.common.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rager.credit.domain.entity.enums.TermTemplateType
import ru.rager.credit.presentation.databinding.ItemTermTemplateBinding
import ru.rager.credit.presentation.util.getLayoutInflater

class TermTemplateListAdapter(private val listener: TermTemplateListener) : RecyclerView.Adapter<TermTemplateListAdapter.TermTemplateViewHolder>() {

    var data: List<TermTemplateType> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermTemplateViewHolder {
        val binding = ItemTermTemplateBinding.inflate(parent.getLayoutInflater(), parent, false)
        return TermTemplateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TermTemplateViewHolder, position: Int) {
        holder.binding.data = data[position]
        holder.binding.listener = listener
    }

    override fun getItemCount(): Int = data.size

    class TermTemplateViewHolder(val binding: ItemTermTemplateBinding) : RecyclerView.ViewHolder(binding.root)

    interface TermTemplateListener {
        fun onSelectTermTemplate(value: TermTemplateType)
    }
}