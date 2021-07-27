package ru.rager.credit.presentation.util

import android.view.LayoutInflater
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

fun View.getLayoutInflater() = LayoutInflater.from(context)

fun TabLayout.setupWithViewPager2(viewPager2: ViewPager2, title: (Int) -> String) {
    TabLayoutMediator(this, viewPager2) { tab, position ->
        tab.text = title(position)
    }.attach()
}