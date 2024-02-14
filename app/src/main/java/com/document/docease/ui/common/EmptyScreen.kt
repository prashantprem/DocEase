package com.document.docease.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.document.docease.R
import com.document.docease.utils.Extensions.emptyMessage
import com.document.docease.utils.ScreenType

@Composable
fun EmptyScreen(
    screenType: ScreenType
) {
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.bg_color_main))
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_no_file_main),
            contentDescription = "No recent files",
            modifier = Modifier.size(200.dp)
        )
        Text(text = screenType.emptyMessage(), style = MaterialTheme.typography.bodyMedium)
    }
}