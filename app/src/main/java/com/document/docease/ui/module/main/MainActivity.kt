package com.document.docease.ui.module.main


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.document.docease.R
import com.document.docease.ui.components.ads.loadInterstitial
import com.document.docease.ui.components.ads.showInterstitial
import com.document.docease.ui.navigation.MainNavigationConfiguration
import com.document.docease.ui.theme.DocEaseTheme
import com.document.docease.utils.AdUnits
import com.document.docease.utils.Constant
import com.document.docease.utils.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel by viewModels<MainViewModel>()

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                viewModel.showSplash
            }
        }
        if (Constant.showAds) {
            loadInterstitial(this@MainActivity, AdUnits.splashInterstitial, onAdLoaded = {
                showInterstitial(this@MainActivity, onAdDismissed = {
                    viewModel.showSplash = false
                })

            }, onAdFailed = {
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

//        PermissionUtils.isPermission(
//            PERMISSION_EXTERNAL,
//            this@MainActivity,
//            requestPermissionResultLauncher
//        ).let { hasPermission ->
//            if (hasPermission) {
//                viewModel.getAllFiles(this@MainActivity)
//            }
//        }


        viewModel.allFiles.observe(this) {
            Log.d("TestingFiles", "$it")
        }
        setContent {
            DocEaseTheme {
                val navController = rememberNavController()
                Box(Modifier.safeDrawingPadding()) {
                    MainNavigationConfiguration(
                        navController = navController,
                        viewModel = viewModel,
                        requestPermissionResultLauncher
                    )
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
