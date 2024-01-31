package com.document.docease.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import com.document.docease.R
import com.document.docease.ui.module.filescreen.FileType
import com.document.docease.ui.theme.excelSelected
import com.document.docease.ui.theme.pdfSelected
import com.document.docease.ui.theme.pptSelected
import com.document.docease.ui.theme.wordSelected
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Extensions {


    fun Long.formatTimeStamp(): String {
        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = Date(this)
        return sdf.format(date)
    }

    fun Long.formatToFileSize(): String {
        val kb: Long = 1024
        val mb = kb * 1024
        val gb = mb * 1024
        return if (this >= gb) {
            String.format("%.1f GB", this.toFloat() / gb)
        } else if (this >= mb) {
            val f = this.toFloat() / mb
            String.format(if (f > 100) "%.0f MB" else "%.1f MB", f)
        } else if (this >= kb) {
            val f = this.toFloat() / kb
            String.format(if (f > 100) "%.0f KB" else "%.1f KB", f)
        } else String.format("%d B", this)
    }

    fun FileType.fileIcon(): Int {
        return when (this) {
            FileType.PDF -> R.drawable.ic_large_pdf
            FileType.WORD -> R.drawable.ic_large_word
            FileType.EXCEL -> R.drawable.ic_large_excel
            FileType.P_POINT -> R.drawable.ic_large_ppt
        }
    }

    fun File.FileType(): FileType {
        return when (this.name.substring(this.name.lastIndexOf(".") + 1)) {
            "pdf" -> FileType.PDF
            "ppt", "pptx" -> FileType.P_POINT
            "doc", "docx" -> FileType.WORD
            "xlsx", "xls" -> FileType.EXCEL
            else -> FileType.PDF
        }
    }

    fun FileType.favouriteTintColor(): Color {
        return when (this) {
            FileType.PDF -> pdfSelected
            FileType.WORD -> wordSelected
            FileType.EXCEL -> excelSelected
            FileType.P_POINT -> pptSelected
        }
    }


    fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }

    fun Context.findActivity(): ComponentActivity? = when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    fun String?.useNonBreakingSpace() = this.orEmpty()
        .replace(
            Constant.REGULAR_SPACE_CHARACTER,
            Constant.NON_BREAKABLE_SPACE_UNICODE
        )


    fun ScreenType.emptyMessage(): String {
        return when (this) {
            ScreenType.FILE, ScreenType.SEARCH -> "No files found!"
            ScreenType.FAVOURITES -> "No file added to favourites!"
            ScreenType.HISTORY -> "Open a file to add in history!"
        }
    }
}