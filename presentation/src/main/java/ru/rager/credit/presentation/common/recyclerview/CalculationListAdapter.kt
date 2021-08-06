package ru.rager.credit.presentation.common.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rager.credit.domain.entity.CreditDateCalculationEntity
import ru.rager.credit.presentation.databinding.ItemSchedulePaymentBinding
import ru.rager.credit.presentation.util.getLayoutInflater

class CalculationListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<CreditDateCalculationEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is CreditDateCalculationEntity.SchedulePaymentCreditDateCalculationEntity -> 1
            is CreditDateCalculationEntity.EarlyPaymentCreditDateCalculationEntity -> 2
            is CreditDateCalculationEntity.RateChangesCreditDateCalculationEntity -> 3
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
            is SchedulePaymentViewHolder -> holder.bind(data[position] as CreditDateCalculationEntity.SchedulePaymentCreditDateCalculationEntity)
        }
    }

    override fun getItemCount() = data.size

    class SchedulePaymentViewHolder(private val binding: ItemSchedulePaymentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(schedulePaymentCreditDateCalculationEntity: CreditDateCalculationEntity.SchedulePaymentCreditDateCalculationEntity) {
            binding.data = schedulePaymentCreditDateCalculationEntity
        }

    }

}