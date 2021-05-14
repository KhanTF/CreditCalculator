package ru.rager.credit.presentation.util.binding.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

object ViewPager2Adapter {

    @BindingAdapter("app:pager_adapter", "app:with_tab_layout", "app:tab_array", requireAll = false)
    @JvmStatic
    fun setAdapter(
        viewPager: ViewPager2,
        adapter: RecyclerView.Adapter<*>?,
        tabLayout: TabLayout?,
        array: Array<String>?
    ) {
        viewPager.adapter = adapter
        if (tabLayout != null && array != null && adapter != null) {
            val mediator = TabLayoutMediator(tabLayout, viewPager) { tab, position -> tab.text = array[position] }
            mediator.attach()
        }
    }

}