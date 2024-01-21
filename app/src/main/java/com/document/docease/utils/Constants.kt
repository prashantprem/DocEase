package com.document.docease.utils

import android.os.Environment
import java.io.File

object Constants {
    val dir = File(Environment.getExternalStorageDirectory().absolutePath)
}