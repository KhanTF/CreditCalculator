package ru.rager.credit.presentation.ui.calculation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CalculationPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        private const val PAGE_COUNT = 2
        const val TAB_GENERAL_POSITION = 0
        const val TAB_PAYMENTS_POSITION = 1
    }

    override fun getItemCount(): Int = PAGE_COUNT

    override fun createFragment(position: Int): Fragment = when (position) {
        TAB_GENERAL_POSITION -> CalculationGeneralFragment.getInstance()
        TAB_PAYMENTS_POSITION -> CalculationPaymentsFragment.getInstance()
        else -> throw IllegalArgumentException("Incorrect position")
    }

}