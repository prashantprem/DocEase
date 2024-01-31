package com.document.docease

import AppOpenAdManager
import com.artifex.sonui.MainApp
import com.document.docease.utils.AdUnits
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : MainApp() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) {
            if (BuildConfig.DEBUG) {
                val testDeviceIds = listOf("AFFEC6633D87C069A5650A2AE9B6CF4B")
                val configuration =
                    RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
                MobileAds.setRequestConfiguration(configuration)
            }
            AppOpenAdManager(this, AdUnits.appOpen)
        }
    }
}