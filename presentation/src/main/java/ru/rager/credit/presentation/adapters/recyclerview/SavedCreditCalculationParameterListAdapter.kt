package ru.rager.credit.presentation.adapters.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rager.credit.domain.entity.CreditCalculationParameterEntity
import ru.rager.credit.presentation.databinding.ItemCalculationBinding
import ru.rager.credit.presentation.util.getLayoutInflater

class SavedCreditCalculationParameterListAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<SavedCreditCalculationParameterListAdapter.CalculationViewHolder>() {

    var data: List<CreditCalculationParameterEntity.SavedCalculationParameterEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculationViewHolder {
        return CalculationViewHolder(ItemCalculationBinding.inflate(parent.getLayoutInflater(), parent, false))
    }

    override fun onBindViewHolder(holder: CalculationViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    override fun getItemCount(): Int = data.size

    class CalculationViewHolder(private val binding: ItemCalculationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            calculationParameterSavedEntity: CreditCalculationParameterEntity.SavedCalculationParameterEntity,
            listener: Listener
        ) {
            binding.data = calculationParameterSavedEntity
            binding.listener = listener
        }

    }

    interface Listener {
        fun onOpen(savedCalculationParameterEntity: CreditCalculationParameterEntity.SavedCalculationParameterEntity)
        fun onDelete(savedCalculationParameterEntity: CreditCalculationParameterEntity.SavedCalculationParameterEntity)
    }

}