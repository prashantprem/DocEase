package com.document.docease.ui.components.ads

import android.content.Context
import android.util.Log
import com.document.docease.utils.Constant
import com.document.docease.utils.Extensions.findActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

val ads: MutableMap<String, InterstitialAd?> = mutableMapOf()
var isInterstitialAdShowing = false
var rewardedAd: RewardedAd? = null
const val AdTag = "AdUtil"
var isInterstitialLoading = false
var isRewardAdLoading = false


fun showInterstitial(context: Context, adUnit: String, onAdDismissed: () -> Unit) {
    if (Constant.showAdsState.value) {
        val activity = context.findActivity()
        if (activity != null && ads[adUnit] != null) {
            ads[adUnit]?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    isInterstitialAdShowing = false
                    ads[adUnit] = null
                    onAdDismissed()
                    Log.d("Interstitial", "Called here-4")
                    log("Interstitial AdFailedToShow")

                }

                override fun onAdDismissedFullScreenContent() {
                    isInterstitialAdShowing = false
                    ads[adUnit] = null
                    loadInterstitial(context, adUnit)
                    onAdDismissed()
                    Log.d("Interstitial", "Called here-5")
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    isInterstitialAdShowing = true
                }
            }
            Log.d("Interstitial", "Called here-6")
            ads[adUnit]?.show(activity)
        } else if (ads[adUnit] == null) {
            Log.d("Interstitial", "Called here-7")
            onAdDismissed()
            loadInterstitial(context, adUnit)
        }
    } else {
        Log.d("Interstitial", "Called here-8")
        onAdDismissed()
    }
}

fun showInterstitialOnClick(
    context: Context,
    adUnit: String,
    clickCount: Int,
    onAdDismissed: () -> Unit,
    finished: () -> Unit
) {
    if (Constant.showAdsState.value) {
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
                    log("Interstitial AdShown")

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
    if (Constant.showAdsState.value && !isInterstitialLoading) {
        isInterstitialLoading = true
        InterstitialAd.load(
            context,
            adUnit,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    isInterstitialLoading = false
                    ads[adUnit] = null
                    if (onAdFailed != null) {
                        onAdFailed()
                    }
                    log("Interstitial AdLoad failed")

                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    isInterstitialLoading = false
                    ads[adUnit] = interstitialAd
                    if (onAdLoaded != null) {
                        onAdLoaded()
                    }
                    log("Interstitial AdLoaded")
                }
            }
        )
    }
}

fun loadRewardedAd(
    context: Context,
    adUnit: String,
    onAdLoaded: (() -> Unit)? = null,
    onAdFailed: (() -> Unit)? = null
) {
    if (Constant.showAdsState.value && !isRewardAdLoading) {
        log("LoadingRewardAd: Started")
        isRewardAdLoading = true
        RewardedAd.load(
            context,
            adUnit,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    rewardedAd = null
                    if (onAdFailed != null) {
                        onAdFailed()
                    }
                    isRewardAdLoading = false
                    log("LoadingRewardAd: Failed")
                }

                override fun onAdLoaded(p0: RewardedAd) {
                    super.onAdLoaded(p0)
                    rewardedAd = p0
                    if (onAdLoaded != null) {
                        onAdLoaded()
                    }
                    isRewardAdLoading = false
                    log("LoadingRewardAd: Loaded")
                }

            }
        )
    }
}

fun showRewardedAd(context: Context, adUnit: String, onAdDismissed: () -> Unit) {
    if (Constant.showAdsState.value) {
        val activity = context.findActivity()
        if (activity != null && rewardedAd != null) {
            rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    isInterstitialAdShowing = false
                    rewardedAd = null
                    onAdDismissed()

                }

                override fun onAdDismissedFullScreenContent() {
                    isInterstitialAdShowing = false
                    rewardedAd = null
                    loadRewardedAd(context, adUnit)
                    onAdDismissed()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    isInterstitialAdShowing = true
                }
            }
            rewardedAd?.show(activity, {})
        } else if (rewardedAd == null) {
            loadRewardedAd(context, adUnit)
        }
    }
}

fun log(msg: String) {
    Log.d(AdTag, msg)
}

