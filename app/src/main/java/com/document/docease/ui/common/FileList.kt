package com.document.docease.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.document.docease.utils.Extensions.convertToFileSize
import com.document.docease.utils.Extensions.formatTimeStamp
import java.io.File

@Composable
fun FileList(
    files: List<File>
) {
    LazyColumn() {
        items(files.size) { index ->
            FileListItem(file = files[index])
        }
    }
}

@Composable
fun FileListItem(file: File) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp).padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_pdf),
            contentDescription = "File icon",
            modifier = Modifier.size(30.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp).fillMaxWidth().weight(1f)) {
            Text(
                text = file.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Row {
                Text(
                    text = file.lastModified().formatTimeStamp(),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
                )
                Text(
                    text = file.length().convertToFileSize(),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "BookMark Button",
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "More Options",
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun previewFileList() {
//    DocEaseTheme {
//        FileListItem(item("Test file.pdf", "21 January 23"))
//    }
//}
//
//data class item(val name: String, val date: String)