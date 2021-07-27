package ru.rager.credit.presentation.common.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rager.credit.domain.entity.CreditEarlyPaymentEntity
import ru.rager.credit.presentation.databinding.ItemEarlyPaymentBinding
import ru.rager.credit.presentation.util.getLayoutInflater

class EarlyPaymentListAdapter(private val listener: Listener) : RecyclerView.Adapter<EarlyPaymentListAdapter.EarlyPaymentViewHolder>() {

    var data = emptyList<CreditEarlyPaymentEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarlyPaymentViewHolder {
        val binding = ItemEarlyPaymentBinding.inflate(parent.getLayoutInflater(), parent, false)
        return EarlyPaymentViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: EarlyPaymentViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class EarlyPaymentViewHolder(private val itemEarlyPaymentBinding: ItemEarlyPaymentBinding, private val listener: Listener) :
        RecyclerView.ViewHolder(itemEarlyPaymentBinding.root) {

        fun bind(earlyPaymentEntity: CreditEarlyPaymentEntity) {
            itemEarlyPaymentBinding.data = earlyPaymentEntity
            itemEarlyPaymentBinding.earlyPaymentDelete.setOnClickListener {
                listener.onDelete(adapterPosition)
            }
        }

    }

    interface Listener {
        fun onDelete(index: Int)
    }
}