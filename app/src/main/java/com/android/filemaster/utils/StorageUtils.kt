package com.android.filemaster.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.storage.StorageManager
import androidx.core.content.ContextCompat.getSystemService
import java.io.File

class StorageUtils {

    val REQ_DOCUMENT_FILE_CODE = 1369

     fun openDocumentFile(file: File, activity: Activity, context: Context) {
        if (Build.VERSION.SDK_INT >= 24) {
            val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
            val storageVolume = storageManager.getStorageVolume(file)
            val intent: Intent
            if (Build.VERSION.SDK_INT >= 29) {
                intent = storageVolume?.createOpenDocumentTreeIntent()!!
            } else {
                intent = storageVolume?.createAccessIntent(null)!!
            }
            activity.startActivityIfNeeded(intent, REQ_DOCUMENT_FILE_CODE)
        } else {
            val intent: Intent
            intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
            activity.startActivityIfNeeded(intent, REQ_DOCUMENT_FILE_CODE)
        }

//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//        intent.type = "*/*"
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
    }
}