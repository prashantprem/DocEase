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

val ads: MutableMap<String, InterstitialAd?> = mutableMapOf()
var isInterstitialAdShowing = false

fun showInterstitial(context: Context, adUnit: String, onAdDismissed: () -> Unit) {
    if (Constant.showAds) {
        val activity = context.findActivity()
        if (activity != null && ads[adUnit] != null) {
            ads[adUnit]?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    isInterstitialAdShowing = false
                    ads[adUnit] = null
                    onAdDismissed()

                }

                override fun onAdDismissedFullScreenContent() {
                    isInterstitialAdShowing = false
                    ads[adUnit] = null
                    loadInterstitial(context, adUnit)
                    onAdDismissed()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    isInterstitialAdShowing = true
                }
            }
            ads[adUnit]?.show(activity)
        } else if (ads[adUnit] == null) {
            loadInterstitial(context, adUnit)
        }
    }
}

fun showInterstitialOnClick(
    context: Context,
    adUnit: String,
    clickCount: Int,
    onAdDismissed: () -> Unit,
    finished: () -> Unit
) {
    if (Constant.showAds) {
        val activity = context.findActivity()
        if (ads[adUnit] != null && activity != null && clickCount >= Constant.adPerClickCount) {
            ads[adUnit]?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    isInterstitialAdShowing = false
                    ads[adUnit] = null
                    finished()
                }

                override fun onAdDismissedFullScreenContent() {
                    isInterstitialAdShowing = false
                    ads[adUnit] = null
                    onAdDismissed()
                    loadInterstitial(context, adUnit)
                    finished()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    isInterstitialAdShowing = true
                }

            }
            ads[adUnit]?.show(activity)
        } else if (ads[adUnit] == null) {
            loadInterstitial(context, adUnit)
            finished()
        } else {
            finished()
        }
    } else {
        finished()
    }

}

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
                    ads[adUnit] = null
                    if (onAdFailed != null) {
                        onAdFailed()
                    }
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    ads[adUnit] = interstitialAd
                    if (onAdLoaded != null) {
                        onAdLoaded()
                    }
                }
            }
        )
    }
}


