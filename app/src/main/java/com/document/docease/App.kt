package com.document.docease

import AppOpenAdManager
import android.content.Context
import com.artifex.sonui.MainApp
import com.document.docease.utils.AdUnits
import com.document.docease.utils.AnalyticsManager
import com.document.docease.utils.RemoteConfigUtil
import com.document.docease.utils.Utility
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : MainApp() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        Utility.checkIfHasToShowAds(this)
        MobileAds.initialize(this) {
            if (BuildConfig.DEBUG) {
                val testDeviceIds = listOf("AFFEC6633D87C069A5650A2AE9B6CF4B")
                val configuration =
                    RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
                MobileAds.setRequestConfiguration(configuration)
            }
        }
        AnalyticsManager.initialize(this)
        RemoteConfigUtil().init()
        AppOpenAdManager(this, AdUnits.appOpen)
    }
}