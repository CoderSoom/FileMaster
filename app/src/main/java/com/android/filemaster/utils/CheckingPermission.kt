package com.android.filemaster.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

object CheckingPermission {
    val REQ_STORAGE_PERMISSION_CODE = 1934
    val REQ_FILE_MANAGER_ACCESS_CODE = 2356
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

    @RequiresApi(api = Build.VERSION_CODES.R)
    fun reqStoreMananger(activity: Activity) {

        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivityForResult(
            intent, REQ_FILE_MANAGER_ACCESS_CODE
        )
    }

    fun isNotStoragePmsGranted(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        } else false
    }


}