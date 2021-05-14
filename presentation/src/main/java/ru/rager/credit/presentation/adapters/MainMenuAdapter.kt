package ru.rager.credit.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rager.credit.presentation.databinding.ItemMainMenuBinding
import ru.rager.credit.presentation.model.MainMenuItem
import ru.rager.credit.presentation.util.getLayoutInflater

class MainMenuAdapter(private val listener: MainMenuListener) : RecyclerView.Adapter<MainMenuAdapter.MainMenuViewHolder>() {

    var mainMenuList = emptyList<MainMenuItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuViewHolder {
        return MainMenuViewHolder(
            binding = ItemMainMenuBinding.inflate(parent.getLayoutInflater(), parent, false),
            listener = listener
        )
    }

    override fun onBindViewHolder(holder: MainMenuViewHolder, position: Int) {
        holder.bind(mainMenuList[position])
    }

    override fun getItemCount(): Int = mainMenuList.size

    class MainMenuViewHolder(
        private val binding: ItemMainMenuBinding,
        private val listener: MainMenuListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mainMenuItem: MainMenuItem) {
            binding.model = mainMenuItem
            binding.listener = listener
        }

    }

    interface MainMenuListener {
        fun onMainMenu(mainMenuItem: MainMenuItem)
    }
}