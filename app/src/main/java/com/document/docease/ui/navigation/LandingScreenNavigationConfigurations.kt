package com.document.docease.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.document.docease.ui.module.filescreen.FileListScreen
import com.document.docease.ui.module.filescreen.FileType
import com.document.docease.ui.module.home.HomeScreen
import com.document.docease.ui.module.main.MainViewModel
import com.document.docease.ui.module.main.bottomnav.BottomNavigationScreens

@Composable
fun LandingScreenNavigationConfigurations(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(navController, startDestination = BottomNavigationScreens.HOME.route) {
        composable(BottomNavigationScreens.HOME.route) {
            HomeScreen(viewModel)
        }
        composable(BottomNavigationScreens.PDF.route) {
            FileListScreen(viewModel, FileType.PDF)
        }
        composable(BottomNavigationScreens.WORD.route) {
            FileListScreen(viewModel, FileType.WORD)
        }
        composable(BottomNavigationScreens.EXCEL.route) {
            FileListScreen(viewModel, FileType.EXCEL)
        }
        composable(BottomNavigationScreens.PPT.route) {
            FileListScreen(viewModel, FileType.P_POINT)
        }
    }
}