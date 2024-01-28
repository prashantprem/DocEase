package com.document.docease

import com.artifex.sonui.MainApp
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : MainApp() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this){
            val testDeviceIds = listOf("AFFEC6633D87C069A5650A2AE9B6CF4B")
            val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
            MobileAds.setRequestConfiguration(configuration)
        }
    }
}