package com.document.docease.ui.navigation

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

@Composable
fun LandingScreenNavigationConfigurations(
    navController: NavHostController,
    viewModel: MainViewModel,
    fileClickListener: FileClickListener
) {
    NavHost(navController, startDestination = BottomNavigationScreens.HOME.route) {
        composable(BottomNavigationScreens.HOME.route) {
            HomeScreen(viewModel, fileClickListener)
        }
        composable(BottomNavigationScreens.PDF.route) {
            FileListScreen(viewModel, FileType.PDF, fileClickListener)
        }
        composable(BottomNavigationScreens.WORD.route) {
            FileListScreen(viewModel, FileType.WORD, fileClickListener)
        }
        composable(BottomNavigationScreens.EXCEL.route) {
            FileListScreen(viewModel, FileType.EXCEL, fileClickListener)
        }
        composable(BottomNavigationScreens.PPT.route) {
            FileListScreen(viewModel, FileType.P_POINT, fileClickListener)
        }
    }
}