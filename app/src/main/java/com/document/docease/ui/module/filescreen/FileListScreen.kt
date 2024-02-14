package com.document.docease.ui.module.filescreen

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.document.docease.R
import com.document.docease.data.Resource
import com.document.docease.ui.common.FileListWrapper
import com.document.docease.ui.common.StoragePermissionScreen
import com.document.docease.ui.module.main.MainActivity
import com.document.docease.ui.module.main.MainViewModel
import com.document.docease.utils.Extensions.fileIcon
import com.document.docease.utils.Extensions.findActivity
import com.document.docease.utils.PermissionUtils
import com.google.android.gms.ads.nativead.NativeAd

@Composable
fun FileListScreen(
    viewModel: MainViewModel,
    fileType: FileType,
    fileClickListener: FileClickListener,
    nativeAd: NativeAd? = null,
    storageRequestLauncher: ActivityResultLauncher<Intent>,
) {
    val mActivity = LocalContext.current.findActivity()
    val hasPermission = PermissionUtils.storagePermissionState.value

    val fileLoadingState = when (fileType) {
        FileType.PDF -> viewModel.pdfFiles.observeAsState()
        FileType.WORD -> viewModel.wordFiles.observeAsState()
        FileType.EXCEL -> viewModel.excelFiles.observeAsState()
        FileType.P_POINT -> viewModel.pptFiles.observeAsState()
    }


    if (mActivity != null && !hasPermission) {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.bg_color_main)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StoragePermissionScreen {
                PermissionUtils.isPermission(
                    MainActivity.PERMISSION_EXTERNAL,
                    activity = mActivity,
                    storageRequestLauncher
                )

            }
        }

    } else {
        when (fileLoadingState.value) {

            is Resource.Loading -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.bg_color_main)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            is Resource.Success -> {
                FileListWrapper(
                    files = fileLoadingState.value?.data!!,
                    fileType.fileIcon(),
                    fileClickListener,
                    nativeAd
                )
            }

            else -> {}
        }
    }


}

//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun previewPddfSCreen() {
//    DocEaseTheme {
//        val viewModel: MainViewModel = hiltViewModel()
//        FileListScreen(viewModel, FileType.PDF)
//    }
//}