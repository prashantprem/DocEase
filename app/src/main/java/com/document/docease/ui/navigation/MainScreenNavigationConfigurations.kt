package com.document.docease.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.document.docease.ui.module.main.LandingScreen
import com.document.docease.ui.module.main.MainViewModel
import com.document.docease.ui.module.search.SearchScreen

@Composable
fun MainNavigationConfiguration(
    navController: NavHostController,
    viewModel: MainViewModel,
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
            LandingScreen(viewModel, navController)
        }
    }
}