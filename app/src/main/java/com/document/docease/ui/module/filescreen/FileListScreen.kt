package com.document.docease.ui.module.filescreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.document.docease.data.Resource
import com.document.docease.ui.common.FileList
import com.document.docease.ui.module.main.MainViewModel
import com.document.docease.ui.theme.DocEaseTheme
import com.document.docease.utils.Extensions.fileIcon

@Composable
fun FileListScreen(
    viewModel: MainViewModel,
    fileType: FileType
) {

    val fileLoadingState = when (fileType) {
        FileType.PDF -> viewModel.pdfFiles.observeAsState()
        FileType.WORD -> viewModel.wordFiles.observeAsState()
        FileType.EXCEL -> viewModel.excelFiles.observeAsState()
        FileType.P_POINT -> viewModel.pptFiles.observeAsState()
    }


    when (fileLoadingState.value) {

        is Resource.Loading -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }

        }

        is Resource.Success -> {
            Column {
                FileList(files = fileLoadingState.value?.data!!, fileType.fileIcon())
            }
        }
        else -> {}
    }

}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun previewPddfSCreen() {
    DocEaseTheme {
        val viewModel: MainViewModel = hiltViewModel()
        FileListScreen(viewModel, FileType.PDF)
    }
}