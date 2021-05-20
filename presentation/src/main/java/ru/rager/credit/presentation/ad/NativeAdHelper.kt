package ru.rager.credit.presentation.ad

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import ru.rager.credit.presentation.BuildConfig
import javax.inject.Inject

class NativeAdHelper<T : Fragment> @Inject constructor(private val fragment: T) : LifecycleObserver {

    val nativeAdLiveData = MutableLiveData<Result<NativeAd>>()
    val nativeAdLiveDataLoading = MutableLiveData<Boolean>()

    private var currentNativeAd: NativeAd? = null

    init {
        fragment.lifecycle.addObserver(this)
    }

    fun refresh() {
        nativeAdLiveDataLoading.value = true
        currentNativeAd?.destroy()
        val builder = AdLoader.Builder(fragment.requireContext(), BuildConfig.KEY_ADD_MAIN_SCREEN)
        builder.forNativeAd { nativeAd ->
            if (isDestroyed(fragment)) {
                currentNativeAd?.destroy()
                nativeAd.destroy()
                nativeAdLiveData.value = Result.failure(Exception("Destroyed"))
            } else {
                currentNativeAd?.destroy()
                currentNativeAd = nativeAd
                nativeAdLiveData.value = Result.success(nativeAd)
            }
        }
        val videoOptions = VideoOptions.Builder()
            .setStartMuted(true)
            .build()
        val adOptions = NativeAdOptions.Builder()
            .setVideoOptions(videoOptions)
            .build()
        builder.withNativeAdOptions(adOptions)
        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                nativeAdLiveData.value = Result.failure(Exception("Failed to load ad"))
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                nativeAdLiveDataLoading.value = false
            }
        }).build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onFragmentCreate() {
        refresh()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onFragmentDestroy() {
        fragment.lifecycle.removeObserver(this)
        currentNativeAd?.destroy()
    }

    private fun isDestroyed(lifecycleOwner: LifecycleOwner): Boolean {
        val lifecycle = lifecycleOwner.lifecycle
        return !lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)
    }

}