package com.document.docease.ui.module.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.document.docease.R
import com.document.docease.ui.module.main.bottomnav.BottomNavigationScreens
import com.document.docease.ui.module.main.bottomnav.CustomBottomNavigation
import com.document.docease.ui.navigation.LandingScreenNavigationConfigurations
import com.document.docease.ui.theme.DocEaseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.HOME,
        BottomNavigationScreens.PDF,
        BottomNavigationScreens.WORD,
        BottomNavigationScreens.EXCEL,
        BottomNavigationScreens.PPT
    )
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.white),
                ),
                title = {
                    Icon(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "Menu Drawer",
                        modifier = Modifier.size(30.dp),
                        tint = Color.Unspecified
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_hamburger),
                            contentDescription = "App Logo",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Button",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                LandingScreenNavigationConfigurations(navController,viewModel)
            }
        },
        bottomBar = {
            CustomBottomNavigation(
                navController = navController,
                items = bottomNavigationItems
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Add, "Create File")
            }
        }
    )


}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun previewLandingSCreen2() {
    DocEaseTheme {
        val viewModel: MainViewModel = hiltViewModel()
        LandingScreen(rememberNavController(), viewModel = viewModel )
    }
}

