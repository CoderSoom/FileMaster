package com.android.filemaster.utils

import android.app.LauncherActivity.ListItem
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.android.filemaster.data.model.FileDefault


class StartFileManager {
val TAG ="hh"
    fun openNomarlFile(context: Context, item: FileDefault) {
        if (item == null) {
            return
        }
        val myMime = MimeTypeMap.getSingleton()
        val intent = Intent(Intent.ACTION_VIEW)
        val parts = item.path!!.split(".").toTypedArray()
        val tail = parts.get(parts.size - 1)
        val mimeType = myMime.getMimeTypeFromExtension(tail)
        Log.d(TAG, "openNomarlFile: "+tail)
        if (TextUtils.isEmpty(mimeType)) {
            intent.setDataAndType(FileManager.createUri(context, item.path), "/")
        } else {
            intent.setDataAndType(FileManager.createUri(context, item.path), mimeType)
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No handler for this type of file.", Toast.LENGTH_LONG).show()
        }
    }

}