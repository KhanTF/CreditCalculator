package ru.rager.credit.presentation.common.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rager.credit.domain.entity.CreditPretermPaymentEntity
import ru.rager.credit.domain.entity.enums.PeriodType
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.databinding.ItemPretermPaymentBinding
import ru.rager.credit.presentation.util.getLayoutInflater

class PretermListAdapter(private val listener: Listener) : RecyclerView.Adapter<PretermListAdapter.EarlyPaymentViewHolder>() {

    var data = emptyList<CreditPretermPaymentEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarlyPaymentViewHolder {
        val binding = ItemPretermPaymentBinding.inflate(parent.getLayoutInflater(), parent, false)
        return EarlyPaymentViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: EarlyPaymentViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class EarlyPaymentViewHolder(private val itemEarlyPaymentBinding: ItemPretermPaymentBinding, private val listener: Listener) :
        RecyclerView.ViewHolder(itemEarlyPaymentBinding.root) {

        fun bind(creditPretermPaymentEntity: CreditPretermPaymentEntity) {
            itemEarlyPaymentBinding.data = creditPretermPaymentEntity
            itemEarlyPaymentBinding.pretermPaymentDelete.setOnClickListener {
                listener.onDelete(adapterPosition)
            }
            itemEarlyPaymentBinding.root.setOnClickListener {
                listener.onEdit(adapterPosition)
            }
            itemEarlyPaymentBinding.executePendingBindings()
        }

    }

    interface Listener {
        fun onEdit(index: Int)
        fun onDelete(index: Int)
    }
}