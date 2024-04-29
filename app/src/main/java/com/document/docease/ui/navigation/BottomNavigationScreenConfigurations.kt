package com.document.docease.ui.navigation

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.document.docease.ui.module.filescreen.FileClickListener
import com.document.docease.ui.module.filescreen.FileListScreen
import com.document.docease.ui.module.filescreen.FileType
import com.document.docease.ui.module.home.HomeScreen
import com.document.docease.ui.module.main.MainViewModel
import com.document.docease.ui.module.main.bottomnav.BottomNavigationScreens
import com.document.docease.utils.DynamicModuleDownloadUtil
import com.google.android.gms.ads.nativead.NativeAd
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner

@Composable
fun BottomNavigationScreenConfigurations(
    navController: NavHostController,
    viewModel: MainViewModel,
    fileClickListener: FileClickListener,
    bottomBarNativeAd: NativeAd?,
    storageRequestLauncher: ActivityResultLauncher<Intent>,
    ad: NativeAd?,
    dynamicModuleDownloadUtil: DynamicModuleDownloadUtil,
    scanner: GmsDocumentScanner,
    scannerLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
) {
    NavHost(navController, startDestination = BottomNavigationScreens.HOME.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(BottomNavigationScreens.HOME.route) {

            HomeScreen(
                viewModel,
                fileClickListener,
                storageRequestLauncher,
                ad,
                dynamicModuleDownloadUtil,
                scannerLauncher,
                scanner
            )
        }
        composable(BottomNavigationScreens.PDF.route) {
            FileListScreen(
                viewModel,
                FileType.PDF,
                fileClickListener,
                bottomBarNativeAd,
                storageRequestLauncher
            )
        }
        composable(BottomNavigationScreens.WORD.route) {
            FileListScreen(
                viewModel,
                FileType.WORD,
                fileClickListener,
                bottomBarNativeAd,
                storageRequestLauncher
            )
        }
        composable(BottomNavigationScreens.EXCEL.route) {
            FileListScreen(
                viewModel,
                FileType.EXCEL,
                fileClickListener,
                bottomBarNativeAd,
                storageRequestLauncher
            )
        }
        composable(BottomNavigationScreens.PPT.route) {
            FileListScreen(
                viewModel,
                FileType.P_POINT,
                fileClickListener,
                bottomBarNativeAd,
                storageRequestLauncher
            )
        }
    }
}