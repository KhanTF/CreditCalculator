package ru.rager.credit.presentation.ui.calculationresult

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.rager.credit.presentation.ui.calculationresult.general.CalculationResultGeneralFragment
import ru.rager.credit.presentation.ui.calculationresult.paymentschedule.CalculationResultPaymentScheduleFragment

class CalculationResultPagerAdapter : FragmentStateAdapter {

    companion object {
        private const val PAGE_COUNT = 2
    }

    constructor(fragment: Fragment) : super(fragment)

    override fun getItemCount(): Int = PAGE_COUNT

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> CalculationResultGeneralFragment.getInstance()
        else -> CalculationResultPaymentScheduleFragment.getInstance()
    }

}