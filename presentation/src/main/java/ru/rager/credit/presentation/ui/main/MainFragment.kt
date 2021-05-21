package ru.rager.credit.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import kotlinx.android.synthetic.main.fragment_main.*
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.ad.NativeAdHelper
import ru.rager.credit.presentation.adapters.recyclerview.MainMenuAdapter
import ru.rager.credit.presentation.databinding.FragmentMainBinding
import ru.rager.credit.presentation.model.MainMenuModel
import ru.rager.credit.presentation.ui.base.BaseIndependentFragment
import ru.rager.credit.presentation.util.itemdecorations.LinearSpaceItemDecoration
import java.util.*
import javax.inject.Inject


class MainFragment : BaseIndependentFragment<MainViewModel, FragmentMainBinding>(), MainMenuAdapter.MainMenuListener {

    companion object {
        fun getInstance() = MainFragment()
    }

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    @Inject
    lateinit var nativeAdHelper: NativeAdHelper<MainFragment>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMainMenuList()
        nativeAdHelper.nativeAdLiveData.observe(viewLifecycleOwner, {
            val nativeAd = it.getOrNull()
            if (nativeAd != null) {
                val nativeAdView = View.inflate(requireContext(), R.layout.layout_native_ad, null) as NativeAdView
                setupNativeAdView(nativeAd, native_add_card, nativeAdView)
            } else {
                native_add_card.removeAllViews()
            }
        })
    }

    private fun setupMainMenuList() {
        val mainMenuAdapter = MainMenuAdapter(this@MainFragment)
        binding.menuList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainMenuAdapter
            addItemDecoration(
                LinearSpaceItemDecoration(
                    start = R.dimen.dp_16,
                    top = R.dimen.dp_16,
                    space = R.dimen.dp_16
                )
            )
        }
        viewModel.mainMenuListLiveData.observe {
            mainMenuAdapter.mainMenuList = it
        }
    }


    private fun setupNativeAdView(nativeAd: NativeAd, nativeAdViewContainer: ViewGroup, nativeAdView: NativeAdView) {
        // Set the media view.
        nativeAdView.mediaView = nativeAdView.findViewById(R.id.ad_media)
        // Set other ad assets.
        nativeAdView.headlineView = nativeAdView.findViewById(R.id.ad_headline)
        nativeAdView.bodyView = nativeAdView.findViewById(R.id.ad_body)
        nativeAdView.callToActionView = nativeAdView.findViewById(R.id.ad_call_to_action)
        nativeAdView.iconView = nativeAdView.findViewById(R.id.ad_app_icon)
        nativeAdView.priceView = nativeAdView.findViewById(R.id.ad_price)
        nativeAdView.starRatingView = nativeAdView.findViewById(R.id.ad_stars)
        nativeAdView.storeView = nativeAdView.findViewById(R.id.ad_store)
        nativeAdView.advertiserView = nativeAdView.findViewById(R.id.ad_advertiser)
        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (nativeAdView.headlineView as TextView).text = nativeAd.headline
        val nativeAdMediaContent = nativeAd.mediaContent
        if (nativeAdMediaContent != null) {
            nativeAdView.mediaView?.setMediaContent(nativeAdMediaContent)
            // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
            // check before trying to display them.
            if (nativeAd.body == null) {
                nativeAdView.bodyView?.visibility = View.INVISIBLE
            } else {
                nativeAdView.bodyView?.visibility = View.VISIBLE
                (nativeAdView.bodyView as TextView).text = nativeAd.body
            }

            if (nativeAd.callToAction == null) {
                nativeAdView.callToActionView?.visibility = View.INVISIBLE
            } else {
                nativeAdView.callToActionView?.visibility = View.VISIBLE
                (nativeAdView.callToActionView as Button).text = nativeAd.callToAction
            }

            val nativeAdIcon = nativeAd.icon
            if (nativeAdIcon == null) {
                nativeAdView.iconView?.visibility = View.GONE
            } else {
                (nativeAdView.iconView as ImageView).setImageDrawable(nativeAdIcon.drawable)
                nativeAdView.iconView?.visibility = View.VISIBLE
            }

            if (nativeAd.price == null) {
                nativeAdView.priceView?.visibility = View.INVISIBLE
            } else {
                nativeAdView.priceView?.visibility = View.VISIBLE
                (nativeAdView.priceView as TextView).text = nativeAd.price
            }

            if (nativeAd.store == null) {
                nativeAdView.storeView?.visibility = View.INVISIBLE
            } else {
                nativeAdView.storeView?.visibility = View.VISIBLE
                (nativeAdView.storeView as TextView).text = nativeAd.store
            }

            if (nativeAd.starRating == null) {
                nativeAdView.starRatingView?.visibility = View.INVISIBLE
            } else {
                (nativeAdView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
                nativeAdView.starRatingView?.visibility = View.VISIBLE
            }

            if (nativeAd.advertiser == null) {
                nativeAdView.advertiserView?.visibility = View.INVISIBLE
            } else {
                (nativeAdView.advertiserView as TextView).text = nativeAd.advertiser
                nativeAdView.advertiserView?.visibility = View.VISIBLE
            }
            nativeAdView.setNativeAd(nativeAd)

            nativeAdViewContainer.removeAllViews()
            nativeAdViewContainer.addView(nativeAdView)
        }
    }

    override fun onMainMenu(mainMenuModel: MainMenuModel) {
        when (mainMenuModel) {
            is MainMenuModel.CalculatePaymentMainMenuModel -> viewModel.onOpenPaymentCalculator()
            is MainMenuModel.CalculatePercentMainMenuModel -> viewModel.onOpenPercentCalculator()
            is MainMenuModel.CalculationListMainMenuModel -> viewModel.onOpenSavedCalculations()
        }
    }


}
