package ru.rager.credit.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rager.credit.domain.entity.CreditPaymentEntity
import ru.rager.credit.presentation.databinding.ItemPaymentBinding
import ru.rager.credit.presentation.util.getLayoutInflater

class PaymentListAdapter : RecyclerView.Adapter<PaymentListAdapter.PaymentViewHolder>() {

    var paymentList: List<CreditPaymentEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder(ItemPaymentBinding.inflate(parent.getLayoutInflater(), parent, false))
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bind(paymentList[position])
    }

    override fun getItemCount() = paymentList.size

    class PaymentViewHolder(private val binding: ItemPaymentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(paymentEntity: CreditPaymentEntity) {
            binding.data = paymentEntity
        }

    }

}