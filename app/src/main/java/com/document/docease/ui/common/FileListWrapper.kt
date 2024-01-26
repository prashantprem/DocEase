package com.document.docease.ui.common

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.document.docease.R
import com.document.docease.utils.Utility
import java.io.File

@Composable
fun FileListWrapper(
    files: List<File>? = null,
    @DrawableRes imageId: Int? = null,
    activity: Activity? = null
) {
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.bg_color_main))
            .fillMaxSize()
    ) {
        if (files != null) {
            FileList(files = files, imageId, onItemCLick = { file ->
                activity?.let {
                    Utility.previewFile(it, file, 0)
                }
            })
        } else {
            EmptyScreen()
        }
    }
}