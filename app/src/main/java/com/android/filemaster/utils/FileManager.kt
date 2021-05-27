package com.android.filemaster.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.database.Cursor
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import com.android.filemaster.FileDefault
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object FileManager {

    val TAG = "giangtd"

    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.Q)
    fun getListDocument(context: Context): MutableList<FileDefault> {

        val documents = mutableListOf<FileDefault>()

        val args =
            arrayOf(
                "%.pdf",
                "%.doc",
                "%.docx",
                "%.xls",
                "%.xlsx",
                "%.ppt",
                "%.pptx",
                "%.txt",
                "%.rtx",
                "%.rtf",
                "%.html"
            )

        val uri = MediaStore.Files.getContentUri("external")
        val projection = arrayOf(
            MediaStore.Files.FileColumns.DATA,

            )
        val selection = StringBuilder()

        args.forEachIndexed { index, elemment ->
            if (index == 0) {
                selection.append(MediaStore.Files.FileColumns.DATA + " like ?")
            } else {
                selection.append(" OR " + MediaStore.Files.FileColumns.DATA + " like ?")
            }
        }
        val cursor: Cursor? =
            context.contentResolver.query(uri, projection, selection.toString(), args, null)

        try {
            cursor?.let {
                val clData = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)

                var hasRow = it.moveToFirst()
                while (hasRow) {
                    val filePath = it.getString(clData)
                    val file = File(filePath)
                    val fileName = file.name
                    val size = convertBytes(file.length())
                    val date = formatDate(file.lastModified())
                    Log.d("xx", file.length().toString())
                    documents.add(FileDefault(fileName, date, size, filePath))
                    hasRow = it.moveToNext()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return documents
    }

    fun formatDate(millis: Long): String {
        @SuppressLint("SimpleDateFormat") val formater: DateFormat =
            SimpleDateFormat("dd/MMM/yyyy - HH:mm")
        return formater.format(Date(millis))
    }

    fun convertBytes(size: Long): String {
        if (size <= 0) {
            return "0"
        }
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(
            size / Math.pow(
                1024.0,
                digitGroups.toDouble()
            )
        ) + " " + units[digitGroups]
    }


    fun getListVideo(context: Context): MutableList<FileDefault> {
        val videos = mutableListOf<FileDefault>()

        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Video.Media.DATA
        )
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        try {
            cursor?.let {
                val clData = it.getColumnIndex(MediaStore.Video.Media.DATA)

                var hasRow = it.moveToFirst()

                while (hasRow) {
                    val filePath = it.getString(clData)
                    val file = File(filePath)
                    val fileName = file.name

                    val size = convertBytes(file.length())
                    val date = formatDate(file.lastModified())

                    videos.add(FileDefault(fileName, date, size, filePath))
                    hasRow = it.moveToNext()
                }
            }
        } finally {
            cursor?.close()
        }
        return videos
    }

    fun deleteFile(context: Context, url: String): Boolean {
        if (checkPathIsSDCard(url)) {
            val uri = getAvailabeAccessDirectoryUri(context, url)
            if (uri != null) {
                return findDocumentFile(context, uri, url)!!.delete()
            } else {
                return false
            }

        } else {
            val fdelete = File(url)
            Log.d(TAG, "deleteImage: file delete: " + fdelete.absolutePath)
            return if (fdelete.exists()) {
                Log.d(TAG, "deleteImage: esxit")
                if (fdelete.delete()) {
                    galleryAddPic(context, url)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }
    }

    private  fun checkPathIsSDCard(path: String): Boolean {
        return !path.contains("/storage/emulated/0/")
    }

    private fun galleryAddPic(context: Context, filePath: String) {
        val file = File(filePath)
        MediaScannerConnection.scanFile(
            context, arrayOf(file.toString()),
            null, null
        )
    }


    private fun getAvailabeAccessDirectoryUri(context: Context, path: String?): Uri? {
        val uriPermissions = context.applicationContext.contentResolver.persistedUriPermissions
        if (uriPermissions.isEmpty()) {
            Log.i(TAG, "getAvailabeAccessDirectoryUri: ")
            return null
        }
        for (uriPermission in uriPermissions) {
            val treeUri: DocumentFile? = getAccessDocumentFile(context, uriPermission.uri, path)
            if (treeUri != null && treeUri.exists()) {
                return uriPermission.uri
            }
        }
        return null
    }

    private fun getAccessDocumentFile(context: Context, rootDir: Uri?, path: String?): DocumentFile? {
        val file = File(path)
        if (file.exists()) {
            Log.i(TAG, "getAccessDocumentFile: ")
            val diretory = DocumentFile.fromTreeUri(context, rootDir!!)
            if (diretory == null || TextUtils.isEmpty(diretory.name)) {
                Log.i(TAG, "getAccessDocumentFile: NULL")
                return null
            }
            try {
                takeUriPermssion(context, rootDir)
                return diretory
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
        return null
    }

    private fun findDocumentFile(context: Context, rootDir: Uri?, path: String?): DocumentFile? {
        val file = File(path)
        if (file.exists()) {
            var pickedDir = DocumentFile.fromTreeUri(context, rootDir!!)

            try {
                takeUriPermssion(context, rootDir)
            } catch (e: SecurityException) {
                e.printStackTrace()
                return null
            }

            val parts = file.path.split("/").toTypedArray()

            if (parts.size == 3) {
                if (pickedDir != null) {
                    return pickedDir.findFile(file.name)
                }
            }

            var filename: String? = ""
            for (i in 3 until parts.size) {
                val part = parts[i]
                if (TextUtils.isEmpty(part)) {
                    break
                }
                if (pickedDir != null) {
                    pickedDir = pickedDir.findFile(part)
                    filename = part
                }
            }

            if (TextUtils.equals(file.name, filename)) {
                return pickedDir
            }
        }
        return null
    }

    private fun takeUriPermssion(context: Context, rootDir: Uri?) {
        val modeFlags =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        context.contentResolver.takePersistableUriPermission(rootDir!!, modeFlags)
    }
    fun getListScreenShots(): java.util.ArrayList<String> {
        val screenShots = java.util.ArrayList<String>()
        var arrFilePic = java.util.ArrayList<File>()
        var arrFileDCMI = java.util.ArrayList<File>()

        val filePic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val fileDCMI = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)

        val screenshotsPic = File(filePic, "Screenshots")
        val screenshotsDCMI = File(fileDCMI, "Screenshots")

        Log.d(TAG, "getScreenShots: url: " + filePic.absolutePath)
        Log.d(TAG, "getScreenShots: url: " + fileDCMI?.absolutePath)
        Log.d(TAG, "getScreenShots: " + screenshotsPic.exists())
        if (screenshotsPic.exists()) {
            screenshotsPic.listFiles()?.let {
                arrFilePic = it.toCollection(java.util.ArrayList())
            }
        }
        if (screenshotsDCMI.exists()) {
            screenshotsDCMI.listFiles()?.let {
                arrFileDCMI = it.toCollection(java.util.ArrayList())
            }
        }

        for (i in arrFilePic) {
            if (!i.isDirectory) {
                screenShots.add(i.absolutePath)
            }
        }
        for (item in arrFileDCMI) {
            if (!item.isDirectory) {
                screenShots.add(item.absolutePath)
            }
        }
        return screenShots
    }
}