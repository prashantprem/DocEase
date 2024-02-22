package com.document.docease.ui.module.create

import android.content.Context
import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.document.docease.R
import com.document.docease.utils.Constant
import com.document.docease.utils.Extensions.noRippleClickable
import com.document.docease.utils.FileAssetModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFile(
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.bar_color)
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.create_document),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = colorResource(
                                id = R.color.text_color
                            ),
                            fontSize = 16.sp
                        )
                    )
                },
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back",
                        tint = colorResource(id = R.color.text_color),
                        modifier = Modifier
                            .noRippleClickable {

                            }
                            .size(25.dp)
                    )

                },
            )

        }

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(color = colorResource(id = R.color.bg_color_main))
                .fillMaxSize()
        ) {
            Constant.getFileAssetList().forEach { file ->
                FileItem(fileAssetModel = file, context = context)
            }

        }

    }
}

@Composable
fun FileItem(fileAssetModel: FileAssetModel, context: Context) {
    val ims = context.assets.open(fileAssetModel.getmIcon())
    val d = BitmapFactory.decodeStream(ims).asImageBitmap()
    Column {
        Image(bitmap = d, contentDescription = null)
        Text(text = fileAssetModel.mType)
    }

}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun previewLandingSCreen2() {
    CreateFile()

}
