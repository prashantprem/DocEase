package com.document.docease.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.document.docease.R
import com.document.docease.ui.components.ads.NativeAdAdmobSmall
import com.document.docease.ui.module.filescreen.FileClickListener
import com.google.android.gms.ads.nativead.NativeAd
import java.io.File

@Composable
fun FileListWrapper(
    files: List<File>? = null,
    @DrawableRes imageId: Int? = null,
    fileClickListener: FileClickListener,
    nativeAd: NativeAd? = null
) {
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.bg_color_main))
            .fillMaxSize()
    ) {
        if (!files.isNullOrEmpty()) {
            NativeAdAdmobSmall(context = LocalContext.current, loadedAd = nativeAd)
            FileList(
                files = files,
                imageId,
                fileClickListener,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        } else {
            EmptyScreen()
        }
    }
}