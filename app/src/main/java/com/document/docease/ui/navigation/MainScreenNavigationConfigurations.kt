package com.document.docease.ui.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.document.docease.ui.module.create.CreateFile
import com.document.docease.ui.module.main.LandingScreen
import com.document.docease.ui.module.main.MainViewModel
import com.document.docease.ui.module.removeads.RemoveAds
import com.document.docease.ui.module.search.SearchScreen
import com.document.docease.utils.DynamicModuleDownloadUtil
import com.google.android.gms.ads.nativead.NativeAd

@Composable
fun MainNavigationConfiguration(
    navController: NavHostController,
    viewModel: MainViewModel,
    storageRequestLauncher: ActivityResultLauncher<Intent>,
    homeNativeAdState: NativeAd?,
    dynamicModuleDownloadUtil: DynamicModuleDownloadUtil
) {
    NavHost(navController, startDestination = Routes.LANDING,
        enterTransition = {
            EnterTransition.None
        },
        popEnterTransition = {
            EnterTransition.None
        },
        popExitTransition = {
            ExitTransition.None
        }
    ) {
        composable(Routes.SEARCH) {
            SearchScreen(viewModel, navController)
        }
        composable(Routes.LANDING) {
            LandingScreen(
                viewModel,
                navController,
                storageRequestLauncher,
                homeNativeAdState,
                dynamicModuleDownloadUtil
            )
        }
        composable(Routes.REMOVE_ADS) {
            RemoveAds(navController)
        }
        composable(Routes.CREATE_DOCUMENTS) {
            CreateFile()
        }
    }
}