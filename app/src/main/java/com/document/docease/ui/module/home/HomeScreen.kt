package com.document.docease.ui.module.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.document.docease.R
import com.document.docease.ui.theme.DocEaseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val tabs = intArrayOf(R.drawable.ic_history,R.drawable.ic_favourites,R.drawable.ic_settings)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        var tabIndex by remember { mutableIntStateOf(0) }
        Image(painter = painterResource(id = R.drawable.ad_placeholder), contentDescription = "Native ad")
        TabRow(selectedTabIndex = tabIndex, modifier = Modifier.padding(vertical = 16.dp)) {
            repeat(3){
                Tab(selected = tabIndex == it, onClick = { tabIndex = it }) {
                    Image(painter = painterResource(id = tabs[it]), contentDescription = "Recent",
                        modifier = Modifier.size(30.dp))
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
        HomeScreen()
    }
}


