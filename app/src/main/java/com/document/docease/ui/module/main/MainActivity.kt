package com.document.docease.ui.module.main


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.document.docease.R
import com.document.docease.ui.components.ads.isInterstitialAdShowing
import com.document.docease.ui.components.ads.loadInterstitial
import com.document.docease.ui.components.ads.rememberNativeAdState
import com.document.docease.ui.components.ads.showInterstitial
import com.document.docease.ui.navigation.MainNavigationConfiguration
import com.document.docease.ui.theme.DocEaseTheme
import com.document.docease.utils.AdUnits
import com.document.docease.utils.AnalyticsManager
import com.document.docease.utils.Constant
import com.document.docease.utils.FirebaseEvents
import com.document.docease.utils.InAppReviewUtil
import com.document.docease.utils.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel by viewModels<MainViewModel>()
    private var inAppReviewUtil: InAppReviewUtil? = null
    private var hasRequestedReviewFlowInSession = false

    companion object {
        const val CODE_RESULT_BOOKMARK = 2
        const val PERMISSION_EXTERNAL = 0x111111
    }


    private val requestPermissionResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    PermissionUtils.storagePermissionState.value = true
                    viewModel.getAllFiles(this@MainActivity)
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.allow_permission_for_storage_access),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isInterstitialAdShowing = true
        installSplashScreen().apply {
            AnalyticsManager.logEvent(FirebaseEvents.splashLaunch)
            this.setKeepOnScreenCondition {
                viewModel.showSplash
            }
        }
        if (Constant.showAds) {
            initSplashAdCountDownTimer()
            loadInterstitial(this@MainActivity, AdUnits.splashInterstitial, onAdLoaded = {
                showInterstitial(this@MainActivity, onAdDismissed = {
                    AnalyticsManager.logEvent(FirebaseEvents.shownAdOnSPlash)
                    viewModel.showSplash = false
                }, adUnit = AdUnits.splashInterstitial)

            }, onAdFailed = {
                AnalyticsManager.logEvent(FirebaseEvents.notShownAdOnSPlash)
                viewModel.showSplash = false
            })
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                delay(3000)
                viewModel.showSplash = false
            }
        }
        if (PermissionUtils.hasPermission(this@MainActivity)) {
            viewModel.getAllFiles(this@MainActivity)
        }

        viewModel.allFiles.observe(this) {
            Log.d("TestingFiles", "$it")
        }
        inAppReviewUtil = InAppReviewUtil(this@MainActivity)
        setContent {
            DocEaseTheme {
                val navController = rememberNavController()
                val pullRefreshState = rememberPullRefreshState(
                    viewModel.isRefreshing,
                    { viewModel.refresh(this@MainActivity) })
                val homeNativeAdState = rememberNativeAdState(
                    context = LocalContext.current,
                    adUnitId = AdUnits.homeNative,
                    refreshInterval = Constant.nativeAdRefreshInterval
                )
                Box(
                    Modifier
                        .safeDrawingPadding()
                        .pullRefresh(pullRefreshState)
                ) {
                    MainNavigationConfiguration(
                        navController = navController,
                        viewModel = viewModel,
                        requestPermissionResultLauncher,
                        homeNativeAdState
                    )
                    if (PermissionUtils.storagePermissionState.value) {
                        PullRefreshIndicator(
                            viewModel.isRefreshing, pullRefreshState,
                            Modifier
                                .align(Alignment.TopCenter)
                                .size(30.dp)
                        )
                    }
                }
            }

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_EXTERNAL) {
            if (grantResults.isNotEmpty() && permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionUtils.storagePermissionState.value = true
                    viewModel.getAllFiles(this@MainActivity)
                } else {
                    PermissionUtils.isPermission(
                        PERMISSION_EXTERNAL,
                        this,
                        requestPermissionResultLauncher
                    )
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onResume() {
        super.onResume()
        if (Constant.hasOpenedAFileInSession && !hasRequestedReviewFlowInSession) {
            hasRequestedReviewFlowInSession = true
            inAppReviewUtil?.showInAppReview()
        }
    }

    private fun initSplashAdCountDownTimer() {
        object : CountDownTimer(
            Constant.splashTime,
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                println("Countdown Seconds remaining: $secondsRemaining")
            }

            override fun onFinish() {
                viewModel.showSplash = false
                println("Countdown finished!")
            }
        }.start()
    }
}

//
//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun previewLandingScreen() {
//    DocEaseTheme {
//        val viewModel: MainViewModel = hiltViewModel()
//        LandingScreen(rememberNavController(), viewModel)
//    }
//}
