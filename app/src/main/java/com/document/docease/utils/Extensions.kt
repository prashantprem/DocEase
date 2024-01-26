package com.document.docease.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.document.docease.R
import com.document.docease.ui.module.filescreen.FileType
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

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
}