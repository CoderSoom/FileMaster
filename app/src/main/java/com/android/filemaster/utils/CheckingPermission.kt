package com.android.filemaster.utils

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat

object CheckingPermission {
    const val REQ_STORAGE_PERMISSION_CODE = 1990
    const val REQUEST_CODE_MANAGE_STORAGE_PERMISSION = 2356
    val PERMISSION_STORAGE: Array<String> = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    fun checkPermission(context: Context, vararg permissions: String): Boolean {
        for (permission in permissions) {
            val check = ActivityCompat.checkSelfPermission(context, permission)
            if (check != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun showPermission(act: Activity, requestCode: Int, vararg permissions: String): Boolean {
        if (checkPermission(act, *permissions)) {
            return true
        }
        ActivityCompat.requestPermissions(
            act, permissions,
            requestCode
        )
        return false
    }

    @TargetApi(Build.VERSION_CODES.R)
    fun requestManageStoragePermission(activity: Activity) {
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivityForResult(
            intent, REQUEST_CODE_MANAGE_STORAGE_PERMISSION
        )
    }

    fun goSettingsForPermission(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:${activity.packageName}")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        activity.startActivity(intent)
    }

    fun isNotStoragePmsGranted(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        }
        return false
    }
}