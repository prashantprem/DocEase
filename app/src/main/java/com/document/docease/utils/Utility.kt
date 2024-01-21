package com.document.docease.utils

object Utility {

    fun isSupportedFileType(fileName: String): Boolean {
        return fileName.endsWith(".docb") || fileName.endsWith(".docx") || fileName.endsWith(".doc") || fileName.endsWith(
            ".dotx"
        ) ||
                fileName.endsWith(".xls") || fileName.endsWith(".xlsx") || fileName.endsWith(".xlt") || fileName.endsWith(
            ".xlm"
        ) ||
                fileName.endsWith(".xlsb") || fileName.endsWith(".ppt") || fileName.endsWith(".pptx") || fileName.endsWith(
            ".pdf"
        ) ||
                fileName.endsWith(".txt")
    }

}