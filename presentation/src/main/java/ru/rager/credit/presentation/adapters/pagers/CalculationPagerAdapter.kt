package ru.rager.credit.presentation.adapters.pagers

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.rager.credit.presentation.ui.calculation.general.CalculationGeneralFragment
import ru.rager.credit.presentation.ui.calculation.payments.CalculationPaymentsFragment

class CalculationPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        private const val PAGE_COUNT = 2
    }

    override fun getItemCount(): Int = PAGE_COUNT

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> CalculationGeneralFragment.getInstance()
        else -> CalculationPaymentsFragment.getInstance()
    }

}