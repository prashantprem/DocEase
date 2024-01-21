package com.document.docease.ui.module.main.bottomnav

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.document.docease.ui.theme.DocEaseTheme

@Composable
fun CustomBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
        Log.d("Testing", currentRoute.toString())
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            IconButton(
                onClick = {
                    navController.navigate(screen.route){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                modifier = Modifier.size(70.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = stringResource(id = screen.label),
                        tint = if (isSelected) screen.selectedColor else Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(id = screen.label),
                        style = TextStyle(
                            color = if (isSelected) screen.selectedColor else Color.Gray,
                            fontSize = 12.sp,
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun previewHomeScreen() {
    DocEaseTheme {
        val bottomNavigationItems = listOf(
            BottomNavigationScreens.HOME,
            BottomNavigationScreens.PDF,
            BottomNavigationScreens.WORD,
            BottomNavigationScreens.EXCEL,
            BottomNavigationScreens.PPT

        )
        CustomBottomNavigation(
            navController = rememberNavController(),
            items = bottomNavigationItems
        )
    }
}