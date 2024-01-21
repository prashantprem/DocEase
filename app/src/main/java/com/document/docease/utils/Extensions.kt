package com.document.docease.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Extensions {


    fun Long.formatTimeStamp(): String {
        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = Date(this)
        return sdf.format(date)
    }

    fun Long.convertToFileSize(): String {
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

}