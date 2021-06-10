package com.android.filemaster.utils

import android.app.LauncherActivity.ListItem
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import com.android.filemaster.BuildConfig
import com.android.filemaster.data.model.FileDefault
import java.io.File


class StartFileManager {
    fun openNomarlFile(context: Context, item: File?) {
        val myMime = MimeTypeMap.getSingleton()
        val intent = Intent(Intent.ACTION_VIEW)
        val mimeType = myMime.getMimeTypeFromExtension(getExtension(item!!.name))
        if (TextUtils.isEmpty(mimeType)) {
            intent.setDataAndType(createUri(context, item.path), "/")
        } else {
            intent.setDataAndType(createUri(context, item.path), mimeType)
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No handler for this type of file.", Toast.LENGTH_LONG).show()
        }
    }

    fun createUri(context: Context?, str: String?): Uri? {
        return if (Build.VERSION.SDK_INT < 24) {
            Uri.fromFile(File(str))
        } else try {
            val provideName: String = BuildConfig.APPLICATION_ID + ".provider"
            FileProvider.getUriForFile(context!!, provideName, File(str))
        } catch (e: IllegalArgumentException) {
            Uri.Builder().build()
        }
    }

    fun getExtension(filename: String?): String? {
        val ext: String?
        val index = indexOfExtension(filename)
        ext = if (filename == null) {
            null
        } else if (index == -1) {
            ""
        } else {
            filename.substring(index + 1)
        }
        return if (!ext.isNullOrEmpty()) {
            ext!!.toLowerCase()
        } else ""
    }

    fun indexOfExtension(filename: String?): Int {
        if (filename == null) {
            return -1
        }
        val extensionPos = filename.lastIndexOf(".")
        val lastSeparator = indexOfLastSeparator(filename)
        return if (lastSeparator > extensionPos) -1 else extensionPos
    }

    fun indexOfLastSeparator(filename: String?): Int {
        if (filename == null) {
            return -1
        }
        val lastUnixPos = filename.lastIndexOf("/")
        val lastWindowsPos = filename.lastIndexOf("\\")
        return Math.max(lastUnixPos, lastWindowsPos)
    }


}