package ru.rager.credit.presentation.ui

import android.os.Bundle
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.databinding.ActivityRootBinding
import ru.rager.credit.presentation.ui.base.BaseActivity


class RootActivity : BaseActivity<RootViewModel, ActivityRootBinding>() {

    override val viewModelClass = RootViewModel::class.java

    override val navigator: Navigator by lazy { AppNavigator(this, R.id.main_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.onOpenMain()
        }
        MobileAds.initialize(this) {

        }
    }

    override fun onBackPressed() {
        viewModel.onBack()
    }

    override fun getViewDataBindingInstance(): ActivityRootBinding {
        return ActivityRootBinding.inflate(layoutInflater)
    }

}