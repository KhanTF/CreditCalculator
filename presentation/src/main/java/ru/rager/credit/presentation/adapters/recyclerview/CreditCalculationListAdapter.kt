package ru.rager.credit.presentation.adapters.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rager.credit.domain.entity.CreditCalculationEntity
import ru.rager.credit.presentation.databinding.ItemSchedulePaymentBinding
import ru.rager.credit.presentation.util.getLayoutInflater

class CreditCalculationListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<CreditCalculationEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is CreditCalculationEntity.SchedulePaymentCreditCalculationEntity -> 1
            is CreditCalculationEntity.EarlyPaymentCreditCalculationEntity -> 2
            is CreditCalculationEntity.RateChangesCreditCalculationEntity -> 3
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> SchedulePaymentViewHolder(ItemSchedulePaymentBinding.inflate(parent.getLayoutInflater(), parent, false))
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SchedulePaymentViewHolder -> holder.bind(data[position] as CreditCalculationEntity.SchedulePaymentCreditCalculationEntity)
        }
    }

    override fun getItemCount() = data.size

    class SchedulePaymentViewHolder(private val binding: ItemSchedulePaymentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(schedulePaymentCreditCalculationEntity: CreditCalculationEntity.SchedulePaymentCreditCalculationEntity) {
            binding.data = schedulePaymentCreditCalculationEntity
        }

    }

}