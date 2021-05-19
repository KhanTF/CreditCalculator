package ru.rager.credit.presentation.adapters.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rager.credit.domain.entity.CreditCalculationPaymentEntity
import ru.rager.credit.presentation.databinding.ItemPaymentBinding
import ru.rager.credit.presentation.util.getLayoutInflater

class CreditCalculationPaymentListAdapter : RecyclerView.Adapter<CreditCalculationPaymentListAdapter.PaymentViewHolder>() {

    var data: List<CreditCalculationPaymentEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder(ItemPaymentBinding.inflate(parent.getLayoutInflater(), parent, false))
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    class PaymentViewHolder(private val binding: ItemPaymentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(calculationPaymentEntity: CreditCalculationPaymentEntity) {
            binding.data = calculationPaymentEntity
        }

    }

}