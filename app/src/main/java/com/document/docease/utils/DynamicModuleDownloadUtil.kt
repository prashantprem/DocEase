package com.document.docease.utils

import android.content.Context
import android.util.Log
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.SplitInstallException
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

const val TAG = "dynamic_module_util"

interface DynamicDeliveryCallback {
    fun onDownloading()
    fun onDownloadCompleted()
    fun onInstallSuccess()
    fun onFailed(errorMessage: String)
}

class DynamicModuleDownloadUtil(context: Context, private val callback: DynamicDeliveryCallback) {

    private lateinit var splitInstallManager: SplitInstallManager
    private var mySessionId = 0
    private var listener: SplitInstallStateUpdatedListener? = null

    init {
        if (!::splitInstallManager.isInitialized) {
            splitInstallManager = SplitInstallManagerFactory.create(context)
        }
    }

    fun isModuleDownloaded(moduleName: String): Boolean {
        return splitInstallManager.installedModules.contains(moduleName)
    }

    fun downloadDynamicModule(moduleName: String, context: Context) {
        val request = SplitInstallRequest.newBuilder()
            .addModule(moduleName)
            .build()

        listener = SplitInstallStateUpdatedListener { state -> handleInstallStates(state, context) }
        listener?.let {
            splitInstallManager.registerListener(listener!!)
        }

        splitInstallManager.startInstall(request)
            .addOnSuccessListener { sessionId ->
                mySessionId = sessionId
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "Exception: $e")
                handleInstallFailure((e as SplitInstallException).errorCode)
            }
    }

    private fun handleInstallFailure(errorCode: Int) {
        when (errorCode) {
            SplitInstallErrorCode.NETWORK_ERROR -> {
                callback.onFailed("No internet found")
            }

            SplitInstallErrorCode.MODULE_UNAVAILABLE -> {
                callback.onFailed("Module unavailable")
            }

            SplitInstallErrorCode.ACTIVE_SESSIONS_LIMIT_EXCEEDED -> {
                callback.onFailed("Active session limit exceeded")
            }

            SplitInstallErrorCode.INSUFFICIENT_STORAGE -> {
                callback.onFailed("Insufficient storage")
            }

            SplitInstallErrorCode.PLAY_STORE_NOT_FOUND -> {
                callback.onFailed("Google Play Store Not Found!")
            }

            else -> {
                callback.onFailed("Something went wrong! Try again later")
            }
        }
    }

    private fun handleInstallStates(state: SplitInstallSessionState, context: Context) {
        if (state.sessionId() == mySessionId) {
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    callback.onDownloading()
                }

                SplitInstallSessionStatus.DOWNLOADED -> {
                    callback.onDownloadCompleted()

                }

                SplitInstallSessionStatus.INSTALLED -> {
                    Log.d(TAG, "Dynamic Module downloaded")
                    SplitCompat.installActivity(context)
                    callback.onInstallSuccess()
                    listener?.let {
                        splitInstallManager.unregisterListener(listener!!)
                    }
                }

                SplitInstallSessionStatus.FAILED -> {
                    callback.onFailed("Installation failed")
                    listener?.let {
                        splitInstallManager.unregisterListener(listener!!)
                    }
                }

                SplitInstallSessionStatus.CANCELED -> {
                    callback.onFailed("Installation Cancelled")
                    listener?.let {
                        splitInstallManager.unregisterListener(listener!!)
                    }
                }

                SplitInstallSessionStatus.CANCELING -> {
                }

                SplitInstallSessionStatus.INSTALLING -> {
                }

                SplitInstallSessionStatus.PENDING -> {
                }

                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                }

                SplitInstallSessionStatus.UNKNOWN -> {
                }
            }
        }
    }

}

