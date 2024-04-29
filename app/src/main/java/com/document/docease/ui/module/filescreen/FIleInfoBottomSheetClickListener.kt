package com.document.docease.ui.module.filescreen

import java.io.File

interface FIleInfoBottomSheetClickListener {

    fun onReadClick(file:File)
    fun onEditClick(file: File)
    fun onWhatsAppShare(file: File)
    fun onShare(file: File)

    fun onPrint(file: File)

    fun onAddToFavourite(file: File)

    fun onSignPdf(file: File)
}