package ru.rager.credit.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.ads.MobileAds
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.databinding.ActivityRootBinding
import ru.rager.credit.presentation.ui.base.BaseActivity


class RootActivity : BaseActivity<RootViewModel, ActivityRootBinding>() {

    override val navController: NavController
        get() = findNavController(R.id.root)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {

        }
    }

    private fun getNavHostFragment() : Fragment{
        return supportFragmentManager.findFragmentById(R.id.navHostFragment) ?: throw IllegalArgumentException("Nav host fragment not found")
    }

}