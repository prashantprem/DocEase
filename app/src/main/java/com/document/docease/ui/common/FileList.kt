package com.document.docease.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.document.docease.R
import com.document.docease.utils.Extensions.formatTimeStamp
import com.document.docease.utils.Extensions.formatToFileSize
import java.io.File
import java.util.Locale

@Composable
fun FileList(
    files: List<File>,
    @DrawableRes imageId: Int,
    onItemCLick: (file: File) -> Unit
) {
    LazyColumn() {
        items(files.size) { index ->
            FileListItem(file = files[index], imageId, onItemCLick)
        }
    }
}

@Composable
fun FileListItem(file: File, imageId: Int, onItemCLick: (file: File) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 10.dp)
            .clickable(onClick = {
                  onItemCLick(file)
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "File icon",
            modifier = Modifier.size(30.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = file.name.lowercase(Locale.getDefault()),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Row {
                Text(
                    text = file.lastModified().formatTimeStamp(),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
                )
                Text(
                    text = file.length().formatToFileSize(),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_star_unfilled),
            contentDescription = "BookMark Button",
            tint = Color.Unspecified,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "More Options",
            tint = Color(0xffB8B8B8),
            modifier = Modifier
                .size(20.dp)
                .clickable(onClick = {})
        )

    }
}

//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun PreviewFileList23() {
//    FileListItem("test", FileType.P_POINT.fileIcon(), onItemCLick = {})
//
//}

