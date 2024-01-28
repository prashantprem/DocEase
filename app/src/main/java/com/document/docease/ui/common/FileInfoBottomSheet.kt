package com.document.docease.ui.common

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.document.docease.R
import com.document.docease.data.dto.FIleInfoBottomSheetData
import com.document.docease.ui.module.filescreen.FIleInfoBottomSheetClickListener
import com.document.docease.utils.Extensions.FileType
import com.document.docease.utils.Extensions.favouriteTintColor
import com.document.docease.utils.Extensions.fileIcon
import com.document.docease.utils.Extensions.formatTimeStamp
import com.document.docease.utils.Extensions.formatToFileSize
import com.document.docease.utils.Extensions.useNonBreakingSpace
import java.io.File

@Composable
fun FileInfoBottomSheetUI(
    file: File,
    isFavourite: Boolean,
    fIleInfoBottomSheetClickListener: FIleInfoBottomSheetClickListener
) {
    var isFavouriteState by remember { mutableStateOf(isFavourite) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(
                    id = file.FileType().fileIcon()
                ),
                contentDescription = "File icon",
                modifier = Modifier
                    .size(40.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = file.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Row {
                    Text(
                        text = file.lastModified().formatTimeStamp(),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                        color = colorResource(id = R.color.text_grey),
                    )
                    Text(
                        text = file.length().formatToFileSize(),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                        modifier = Modifier.padding(start = 8.dp),
                        color = colorResource(id = R.color.text_grey),
                    )
                }
                Text(
                    text = "Location: ${file.absolutePath}".useNonBreakingSpace(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 8.sp,
                        lineBreak = LineBreak.Paragraph.copy(strictness = LineBreak.Strictness.Loose)
                    ),
                    color = colorResource(id = R.color.text_grey),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth(),


                    )
            }
            Spacer(modifier = Modifier.width(10.dp))


            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(id = if (isFavouriteState) R.drawable.ic_star_filled else R.drawable.ic_star_unfilled),
                contentDescription = "More Options",
                tint = if (isFavouriteState) file.FileType().favouriteTintColor() else Color(
                    0xffB8B8B8
                ),
                modifier = Modifier
                    .size(25.dp)
                    .clickable(onClick = {
                        isFavouriteState = !isFavouriteState
                        fIleInfoBottomSheetClickListener.onAddToFavourite(file)
                    })
            )
        }

        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))
        val options = listOf(
            FIleInfoBottomSheetData(stringResource(id = R.string.edit), R.drawable.ic_edit_main),
            FIleInfoBottomSheetData(stringResource(id = R.string.whatsapp), R.drawable.ic_whatsapp),
            FIleInfoBottomSheetData(stringResource(id = R.string.share), R.drawable.ic_share_main),
            FIleInfoBottomSheetData(stringResource(id = R.string.print), R.drawable.ic_print)
        )
        options.forEachIndexed { index, item ->

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .clickable {
                        fIleInfoBottomSheetClickListener.apply {
                            when (index) {
                                0 -> onEditClick(file)
                                1 -> onWhatsAppShare(file)
                                2 -> onShare(file)
                                3 -> onPrint(file)
                            }
                        }
                    }

            ) {
                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 5.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item.title, style = MaterialTheme.typography.bodyMedium.copy(color = colorResource(
                    id = R.color.text_grey
                )))
            }

        }

    }

}


//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun previewBottomSheet() {
//    FileInfoBottomSheetUI(File(""))
//
//}