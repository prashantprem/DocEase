package com.document.docease.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat

object PermissionUtils {
    fun isPermission(
        requestCode: Int,
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>
    ): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                    val uri = Uri.fromParts("package", activity.packageName, null)
                    intent.data = uri
                    launcher.launch(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                false
            } else {
                true
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                activity.requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    requestCode
                )
                false
            } else {
                true
            }
        }
    }

    fun hasPermission(activity: Activity): Boolean {
        val hasStoragePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
        storagePermissionState.value = hasStoragePermission
        return hasStoragePermission
    }

    var storagePermissionState = mutableStateOf(false)
}
