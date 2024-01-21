package com.document.docease.ui.module.pdf

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.document.docease.ui.common.FileList
import com.document.docease.ui.module.main.MainViewModel
import com.document.docease.ui.theme.DocEaseTheme

@Composable
fun PdfScreen(
    viewModel: MainViewModel
) {

    val files = viewModel.allFiles.observeAsState()
    Column {
        FileList(files = files.value!!)
    }


}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun previewPddfSCreen() {
    DocEaseTheme {
        val viewModel: MainViewModel = hiltViewModel()
        PdfScreen(viewModel)
    }
}