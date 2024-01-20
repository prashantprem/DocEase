package com.document.docease.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.document.docease.ui.module.home.HomeScreen
import com.document.docease.ui.module.main.bottomnav.BottomNavigationScreens
import com.document.docease.ui.module.pdf.PdfScreen

@Composable
fun LandingScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavigationScreens.HOME.route) {
        composable(BottomNavigationScreens.HOME.route) {
            HomeScreen()
        }
        composable(BottomNavigationScreens.PDF.route) {
            PdfScreen()
        }
        composable(BottomNavigationScreens.EXCEL.route) {
        }
        composable(BottomNavigationScreens.PPT.route) {
        }
    }


}