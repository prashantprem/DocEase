package com.document.docease.ui.module.filescreen

import java.io.File

interface FileClickListener {
    fun onMenuClick(file: File)
    fun onFileClick(file: File)
}