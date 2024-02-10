package com.document.docease.utils

import android.util.Log
import com.document.docease.utils.Constant.ADS_ENABLED
import com.document.docease.utils.Constant.APP_OPEN_THRESHOLD
import com.document.docease.utils.Constant.FLOW_AD_CLICK_THRESHOLD
import com.document.docease.utils.Constant.NATIVE_AD_REFRESH_TIME_IN_MILLIS
import com.document.docease.utils.Constant.SPLASH_TIME
import com.document.docease.utils.Constant.adPerClickCount
import com.document.docease.utils.Constant.appOpenTimeout
import com.document.docease.utils.Constant.nativeAdRefreshInterval
import com.document.docease.utils.Constant.splashTime
import com.document.docease.utils.Extensions.tryCatch
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class RemoteConfigUtil {

    private val DEFAULTS: HashMap<String, Any> =
        hashMapOf(
            APP_OPEN_THRESHOLD to 5000,
            FLOW_AD_CLICK_THRESHOLD to 4,
            ADS_ENABLED to true,
            NATIVE_AD_REFRESH_TIME_IN_MILLIS to 10000L,
            SPLASH_TIME to 6000L
        )

    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun init() = {
        remoteConfig = getFirebaseRemoteConfig()
        remoteConfig.apply {
//                showAds = getBoolean(ADS_ENABLED)
            appOpenTimeout = getLong(APP_OPEN_THRESHOLD).toInt()
            if (appOpenTimeout == 0) {
                appOpenTimeout = 6000
            }
            adPerClickCount = getDouble(FLOW_AD_CLICK_THRESHOLD).toInt()
            if (adPerClickCount == 0) {
                adPerClickCount = 3
            }
            nativeAdRefreshInterval = getLong(NATIVE_AD_REFRESH_TIME_IN_MILLIS)
            splashTime = getLong(SPLASH_TIME)
            log(
                """
                    $ADS_ENABLED :: ${Constant.showAdsState.value}
                    $FLOW_AD_CLICK_THRESHOLD :: $adPerClickCount
                    $APP_OPEN_THRESHOLD :: $appOpenTimeout
                    $NATIVE_AD_REFRESH_TIME_IN_MILLIS :: $nativeAdRefreshInterval
                    $SPLASH_TIME :: $splashTime
                """.trimIndent()
            )
        }
    }.tryCatch()

    fun log(msg: String) {
        Log.d("RemoteConfig", msg)
    }

    private fun getFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(DEFAULTS as Map<String, Any>)
        remoteConfig.fetchAndActivate().addOnCompleteListener {}
        return remoteConfig
    }


}