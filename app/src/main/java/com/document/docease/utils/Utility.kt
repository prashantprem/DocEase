package com.document.docease.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.core.content.FileProvider
import com.document.docease.BuildConfig
import com.document.docease.ui.module.editors.ViewEditorActivity
import com.document.docease.ui.module.main.MainActivity
import com.document.docease.ui.module.preview.PreviewActivity
import java.io.File

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


    fun fileType(file: File): Int {
        return if (file.isDirectory) {
            1
        } else {
            val type: Int = when (file.name.substring(file.name.lastIndexOf(".") + 1)) {
                "mp3" -> 2
                "wav" -> 2
                "flac" -> 2
                "ape" -> 2
                "java" -> 4
                "html" -> 4
                "js" -> 4
                "css" -> 4
                "json" -> 4
                "xml" -> 4
                "xlsx" -> 5
                "xls" -> 5
                "png" -> 6
                "jpg" -> 6
                "jpeg" -> 6
                "gif" -> 6
                "bmp" -> 6
                "pdf" -> 7
                "ppt" -> 8
                "pptx" -> 8
                "txt" -> 9
                "mp4" -> 10
                "flv" -> 10
                "avi" -> 10
                "3gp" -> 10
                "mkv" -> 10
                "rmvb" -> 10
                "wmv" -> 10
                "doc" -> 11
                "docx" -> 11
                "rar" -> 12
                "zip" -> 12
                else -> 3
            }
            type
        }
    }

    fun shareDocument(file: File?, context: Context) {
        try {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "application/*"
            val uri = FileProvider.getUriForFile(
                context, BuildConfig.APPLICATION_ID + ".fileprovider",
                file!!
            )
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            val resInfoList: List<ResolveInfo> = context.packageManager
                .queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY)
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                context.grantUriPermission(
                    packageName,
                    uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun isDefaultReaderSet(mFile: Uri?, context: Context): Boolean {
        val defaultReaderPackageName = getDefaultReader(mFile, context)
        return defaultReaderPackageName != Constant.ANDROID
    }

    private fun getDefaultReader(fromFile: Uri?, context: Context): String? {
        val intent = Intent(Intent.ACTION_VIEW, fromFile)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        val resolveInfo = context.packageManager.resolveActivity(intent, 0)
        return if (resolveInfo != null) {
            resolveInfo.activityInfo.packageName
        } else "error"
    }


    fun openFile(activity: Activity, mFile: File, pageNumber: Int) {
        val storageUtils = StorageUtils(activity)
        storageUtils.addRecent( mFile)
        val fromFile = Uri.fromFile(mFile)
        val intent = Intent(activity, ViewEditorActivity::class.java)
        intent.action = Constant.INTENT_ACTION_VIEW
        intent.data = fromFile
        intent.putExtra(Constant.STARTED_FROM_EXPLORER, true)
        intent.putExtra(Constant.START_PAGE, pageNumber)
        intent.putExtra(Constant.FILE_NAME, mFile.name)
        intent.putExtra(Constant.SHOW_DEFAULT_BS, false)
        intent.putExtra(Constant.EDITOR, true)
        activity.startActivityForResult(intent, MainActivity.CODE_RESULT_BOOKMARK)
    }

    fun openFileFromUri(activity: Activity, uri: Uri, isForPrint: Boolean = false) {
        val intent = Intent(activity, ViewEditorActivity::class.java)
        intent.action = Constant.INTENT_ACTION_VIEW
        intent.data = uri
        intent.putExtra(Constant.STARTED_FROM_EXPLORER, true)
        intent.putExtra(Constant.START_PAGE, 0)
        intent.putExtra(Constant.SHOW_DEFAULT_BS, false)
        intent.putExtra(Constant.EDITOR, true)
        intent.putExtra(Constant.PRINT, isForPrint)
        activity.startActivity(intent)
    }

    fun previewFile( activity: Activity, mFile: File, pageNumber: Int, isForPrint: Boolean = false) {
        val storageUtils = StorageUtils(activity)
        storageUtils.addRecent( mFile)
        val fromFile = Uri.fromFile(mFile)
        val intent = Intent(activity, PreviewActivity::class.java)
        intent.action = Constant.INTENT_ACTION_VIEW
        intent.data = fromFile
        intent.putExtra(Constant.STARTED_FROM_EXPLORER, true)
        intent.putExtra(Constant.START_PAGE, pageNumber)
        intent.putExtra(Constant.FILE_NAME, mFile.name)
        intent.putExtra(Constant.PREVIEW, true)
        intent.putExtra(Constant.FILE, mFile)
        intent.putExtra(Constant.PRINT, isForPrint)
        if (mFile.name.endsWith(".pdf")) {
            intent.putExtra("isPdf", true)
        }
        activity.startActivityForResult(intent, MainActivity.CODE_RESULT_BOOKMARK)
    }


}