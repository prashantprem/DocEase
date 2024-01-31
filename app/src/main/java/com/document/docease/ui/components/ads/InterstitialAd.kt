package com.document.docease.ui.components.ads

import android.content.Context
import com.document.docease.utils.Constant
import com.document.docease.utils.Extensions.findActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

var mInterstitialAd: InterstitialAd? = null

fun loadInterstitial(
    context: Context,
    adUnit: String,
    onAdLoaded: (() -> Unit)? = null,
    onAdFailed: (() -> Unit)? = null
) {
    if (Constant.showAds) {
        InterstitialAd.load(
            context,
            adUnit,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    if (onAdFailed != null) {
                        onAdFailed()
                    }
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    if (onAdLoaded != null) {
                        onAdLoaded()
                    }
                }
            }
        )
    }

}

fun showInterstitial(context: Context, adUnit: String? = null, onAdDismissed: () -> Unit) {
    if (Constant.showAds) {
        val activity = context.findActivity()
        if (mInterstitialAd != null && activity != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    mInterstitialAd = null
                    onAdDismissed()

                }

                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null
                    adUnit?.let {
                        loadInterstitial(context, it)
                    }
                    onAdDismissed()
                }
            }
            mInterstitialAd?.show(activity)
        } else if (mInterstitialAd == null) {
            adUnit?.let {
                loadInterstitial(context, it)
            }
        }
    }

}

fun showInterstitialOnClick(
    context: Context,
    adUnit: String? = null,
    clickCount: Int,
    onAdDismissed: () -> Unit,
    finished: () -> Unit
) {
    if (Constant.showAds) {
        val activity = context.findActivity()
        if (mInterstitialAd != null && activity != null && clickCount >= Constant.adPerClickCount) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    mInterstitialAd = null
                    finished()
                }

                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null
                    onAdDismissed()
                    adUnit?.let {
                        loadInterstitial(context, it)
                    }
                    finished()
                }
            }
            mInterstitialAd?.show(activity)
        } else if (mInterstitialAd == null) {
            adUnit?.let {
                loadInterstitial(context, it)
            }
            finished()
        } else {
            finished()
        }
    } else {
        finished()
    }

}


fun removeInterstitial() {
    mInterstitialAd?.fullScreenContentCallback = null
    mInterstitialAd = null
}