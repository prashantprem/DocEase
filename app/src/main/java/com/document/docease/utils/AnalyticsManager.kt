package com.document.docease.utils

import android.app.Activity
import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import java.io.File

object AnalyticsManager {
    private val binaryPlaces = arrayOf(
        "/data/bin/", "/system/bin/", "/system/xbin/", "/sbin/",
        "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/",
        "/data/local/"
    )
    private var sAppContext: Context? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var isFirstActionCalled = false

    private fun canSend(): Boolean {
        return sAppContext != null && mFirebaseAnalytics != null
    }

    private fun canPush(): Boolean {
        return sAppContext != null
    }

    @Synchronized
    fun initialize(context: Context) {
        sAppContext = context
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context)
        setProperty("DeviceType", getDeviceType(context))
        setProperty("Rooted", java.lang.Boolean.toString(isRooted()))
    }

    private fun setProperty(propertyName: String, propertyValue: String) {
        if (canSend()) {
            mFirebaseAnalytics?.setUserProperty(propertyName, propertyValue)
        }
    }

    fun logEvent(eventName: String?) {
        if (canSend()) {
            mFirebaseAnalytics?.logEvent(eventName!!, Bundle())
        }
    }

    fun logEvent(eventName: String?, params: Bundle?) {
        if (canSend()) {
            mFirebaseAnalytics?.logEvent(eventName!!, params)
        }
    }

    fun logFirstActionEvent(action: String) {
        if (!isFirstActionCalled) {
            isFirstActionCalled = true
            mFirebaseAnalytics?.logEvent(
                "FirstAction", bundleOf(
                    "action" to action
                )
            )
        }

    }

    fun logEvent(eventName: String, rootInfo: String?, params: Bundle?) {
        var eventName = eventName
        if (canSend()) {
            if (null != rootInfo) {
                eventName = eventName + "_" + rootInfo
                mFirebaseAnalytics?.logEvent(eventName, params)
            }
        }
    }

    fun setCurrentScreen(activity: Activity?, screenName: String?) {
        if (canSend()) {
            if (null != screenName) {
                mFirebaseAnalytics?.setCurrentScreen(activity!!, screenName, screenName)
            }
        }
    }

    private fun getDeviceType(c: Context): String {
        val uiModeManager = c.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        val modeType = uiModeManager.currentModeType
        return when (modeType) {
            Configuration.UI_MODE_TYPE_TELEVISION -> "TELEVISION"
            Configuration.UI_MODE_TYPE_WATCH -> "WATCH"
            Configuration.UI_MODE_TYPE_NORMAL -> if (isTablet(c)) "TABLET" else "PHONE"
            Configuration.UI_MODE_TYPE_UNDEFINED -> "UNKOWN"
            else -> ""
        }
    }

    private fun isRooted(): Boolean {
        for (p in binaryPlaces) {
            val su = File(p + "su")
            if (su.exists()) {
                return true
            }
        }
        return false
    }

    private fun isTablet(context: Context): Boolean {
        return context.resources.configuration.smallestScreenWidthDp >= 600
    }
}