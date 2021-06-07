package com.android.filemaster.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.database.Cursor
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import com.android.filemaster.R
import com.android.filemaster.data.model.FileCustom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log10
import kotlin.math.pow


object FileManager {
    var listImg = arrayListOf<FileCustom>()
    val TAG = "giangtd"

    @Throws(IOException::class)
    fun copy(src: File?, dst: File?) {
        val input = FileInputStream(src)
        try {
            val out = FileOutputStream(dst)
            try {

                val buf = ByteArray(1024)
                var len: Int
                while (input.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
            } finally {
                out.close()
            }
        } finally {
            input.close()
        }
    }

    @Throws(IOException::class)
    fun moveFile(file: File, dir: File) {
        val newFile = File(dir, file.name)
        var outputChannel: FileChannel? = null
        var inputChannel: FileChannel? = null
        try {
            outputChannel = FileOutputStream(newFile).channel
            inputChannel = FileInputStream(file).channel
            inputChannel.transferTo(0, inputChannel.size(), outputChannel)
            inputChannel.close()
            file.delete()
        } finally {
            inputChannel?.close()
            outputChannel?.close()
        }
    }

    fun setImageFile(path: String): Int {
        val parts = path.split(".").toTypedArray()
        Log.d(TAG, "setImageFile: " + parts.get(parts.size - 1))
        val tail = parts.get(parts.size - 1)
        when (tail) {
            Constant.TF_MP3 -> {
                return R.drawable.ic_audio
            }
            Constant.TF_DOC, Constant.TF_DOCX, Constant.TF_RTF, Constant.TF_RTX -> {
                return R.drawable.ic_doc
            }
            Constant.TF_MOV, Constant.TF_MP4, Constant.TF_MPEG4 -> {
                return R.drawable.ic_video
            }
            Constant.TF_HTML -> {
                return R.drawable.ic_html
            }
            Constant.TF_PDF -> {
                return R.drawable.ic_file_pdf
            }
            Constant.TF_ZIP -> {
                return R.drawable.ic_file_zip
            }
            Constant.TF_APK -> {
                return R.drawable.ic_file_apk
            }
            Constant.TF_PPT, Constant.TF_PPTX -> {
                return R.drawable.ic_file_ppt
            }
            Constant.TF_XLS, Constant.TF_XLSX -> {
                return R.drawable.ic_file_elsx
            }
            Constant.TF_RAR -> {
                return R.drawable.ic_file_rar
            }
            Constant.TF_JPG, Constant.TF_PNG ->{
              return 1
            }
            Constant.TF_MORE ->{
                return R.drawable.ic_more_file
            }
        }
        return R.drawable.ic_file_none
    }

    fun getListImage(context: Context): ArrayList<FileCustom> {

        val images: ArrayList<FileCustom> = ArrayList()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        Log.d(TAG, uri.toString())

        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE
        )
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        val cursor = context.contentResolver
            .query(uri, projection, null, null, "$orderBy DESC")!!

        cursor.moveToFirst()
        try {
            while (!cursor.isAfterLast) {
                val absolutePathOfImage =
                    cursor!!.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                val absoluteNameImage =
                    cursor!!.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
                val absoluteSizeImage =
                    cursor!!.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE))
                val sizeMB = getFileSize(absoluteSizeImage)

                var filePath = File(absolutePathOfImage)
                var dateLast = filePath.lastModified()
                var date = Date(dateLast)

                images.add(
                    FileCustom(
                        absoluteNameImage,
                        absolutePathOfImage,
                        sizeMB,
                        date.toString()
                    )
                )
                cursor.moveToNext()


            }
        } finally {
            cursor.close()
        }
        return images
    }


    fun getListAudio(context: Context): ArrayList<FileCustom> {
        val audios: ArrayList<FileCustom> = ArrayList<FileCustom>()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.MIME_TYPE,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DATE_MODIFIED
        )

        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        try {
            cursor?.let {
                var hasRow = it.moveToFirst()
                val indexAlbumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
                while (hasRow) {
                    val img = it.getString(indexAlbumId)
                    val nameAudio =
                        it.getString(it.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    val filePath = it.getString(it.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val sizeAudio = it.getLong(it.getColumnIndex(MediaStore.Audio.Media.SIZE))
                    val mime = it.getString(it.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE))
                    var sizeMB = getFileSize(sizeAudio)
                    val dateAudio =
                        it.getLong(it.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED))
                    var dateeee = getDate(dateAudio, "dd/MM/yyyy hh:mm:ss")

                    val imgAudio = "content://media/external/audio/albumart/$img"
                    var fileThumb = File(filePath)
                    var dateLast = fileThumb.lastModified()
                    var date = Date(dateLast)

                    if (sizeAudio != null) {
                        audios.add(FileCustom(nameAudio, imgAudio, null, null))
                        Log.d(TAG, "name" + filePath)
                    }
                    hasRow = it.moveToNext()
                }
            }
        } finally {
            cursor?.close()
        }
        return audios
    }

    fun getDate(milliSeconds: Long, dateFormat: String?): String? {

        val formatter = SimpleDateFormat(dateFormat)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    fun getFileSize(size: Long): String? {
        if (size <= 0) return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble()))
            .toString() + " " + units[digitGroups]
    }

    suspend fun getListVideo(context: Context): ArrayList<String> =
        withContext(Dispatchers.Default) {
            val videos: ArrayList<String> = ArrayList<String>()

            val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media._ID
            )
            val cursor = context.contentResolver.query(uri, projection, null, null, null)
            try {
                cursor?.let {
                    val clData = it.getColumnIndex(MediaStore.Video.Media.DATA)
                    val clDateAdded = it.getColumnIndex(MediaStore.Video.Media.DATE_ADDED)
                    val clID = it.getColumnIndex(MediaStore.Video.Media._ID)
                    var hasRow = it.moveToFirst()

                    while (hasRow) {
                        val filePath = it.getString(clData)
                        videos.add(filePath)
                        val id = it.getString(clID)
                        hasRow = it.moveToNext()
                    }
                }
            } finally {
                cursor?.close()
            }
            videos
        }

    suspend fun getListDocument(context: Context): ArrayList<String> =
        withContext(Dispatchers.Default) {
            val documents = ArrayList<String>()

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

            val projection = arrayOf(MediaStore.Files.FileColumns.DATA)

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
                    val clDateAdded = it.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED)
                    val clID = it.getColumnIndex(MediaStore.Files.FileColumns._ID)
                    var hasRow = it.moveToFirst()

                    while (hasRow) {
                        val filePath = it.getString(clData)
                        filePath.endsWith(".txt")  // lấy loại file
                        documents.add(filePath)
//                    val id = it.getString(clID)
                        hasRow = it.moveToNext()
                    }
                }
            } finally {
                cursor?.close()
            }
            documents
        }

    suspend fun getListApk(context: Context): ArrayList<String> =
        withContext(Dispatchers.Default) {
            val apks = ArrayList<String>()

            val selectionArgs = arrayOf("%.apk")
            val selection = MediaStore.Files.FileColumns.DATA + " like ?"
            val uri = MediaStore.Files.getContentUri("external")
            val projection = arrayOf(MediaStore.Files.FileColumns.DATA)

            val cursor: Cursor? =
                context.getContentResolver()
                    .query(uri, projection, selection, selectionArgs, null)

            try {
                cursor?.let {
                    val clData = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                    var hasRow = it.moveToFirst()

                    while (hasRow) {
                        val filePath = it.getString(clData)
                        filePath.endsWith(".txt")  // lấy loại file

                        apks.add(filePath)
                        hasRow = it.moveToNext()
                    }
                }
            } finally {
                cursor?.close()
            }
            apks
        }

    suspend fun getListDownload(): ArrayList<File> = withContext(Dispatchers.Default) {
        val downloads = ArrayList<File>()

        val file =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val arrFile = file.listFiles().toCollection(ArrayList())

        for (i in arrFile.toList()) {
            if (!i.isDirectory) {
                downloads.add(i)
            }
        }
        downloads
    }

    suspend fun getListZip(context: Context): ArrayList<String> =
        withContext(Dispatchers.Default) {
            val archives = ArrayList<String>()
            val selectionArgs = arrayOf(
                "%.r__",
                "%.a__",
                "%.z__",
                "%.zipx",
                "%.jar",
                "%.7z",
                "%.gz",
                "%.tgz",
                "%.bz2",
                "%.bz",
                "%.tbz",
                "%.tbz2",
                "%.xz",
                "%.txz",
                "%.lz",
                "%.tlz",
                "%.tar",
                "%.iso",
                "%.lzh",
                "%.lha",
                "%.z",
                "%.taz",
                "%.001"
            )

            val uri = MediaStore.Files.getContentUri("external")
            val projection = arrayOf(MediaStore.Files.FileColumns.DATA)
            val selection = StringBuilder()

            selectionArgs.forEachIndexed { index, elemment ->
                if (index == 0) {
                    selection.append(MediaStore.Files.FileColumns.DATA + " like ?")
                } else {
                    selection.append(" OR " + MediaStore.Files.FileColumns.DATA + " like ?")
                }
            }

            val cursor: Cursor? =
                context.getContentResolver()
                    .query(uri, projection, selection.toString(), selectionArgs, null)
            try {
                cursor?.let {
                    val clData = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                    var hasRow = it.moveToFirst()

                    while (hasRow) {
                        val filePath = it.getString(clData)
                        archives.add(filePath)
                        hasRow = it.moveToNext()
                    }
                }
            } finally {
                cursor?.close()
            }
            archives
        }

    @SuppressLint("QueryPermissionsNeeded")
    suspend fun getListApp(context: Context): ArrayList<String> =
        withContext(Dispatchers.Default) {
            val listApp = ArrayList<String>()
            val apps = context.packageManager.getInstalledApplications(0)
            for (app in apps) {
                listApp.add(app.processName)

                if (app.flags and (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP or ApplicationInfo.FLAG_SYSTEM) > 0) {
                    // It is a system app
                } else {
                    // It is installed by the user
                }
            }
            listApp
        }

    fun getListAccess(context: Context): ArrayList<FileCustom> {
        val accessFiles = ArrayList<FileCustom>()

        return accessFiles
    }

    fun getListRecent(
        context: Context
    ): MutableList<FileCustom> {
        val apks = mutableListOf<FileCustom>()
        val sort = MediaStore.MediaColumns.DATE_ADDED + " DESC"
        val uri = MediaStore.Files.getContentUri("external")
        val projection =
            arrayOf(
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.MIME_TYPE
            )
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -7)
        val timeInMillis = calendar.timeInMillis / 1000

        val cursor: Cursor? = context.contentResolver.query(
            uri,
            projection,
            "date_modified" + ">?",
            arrayOf("" + timeInMillis),
            sort
        )

        try {
            cursor?.let {
                val clData = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                val clName = it.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
                val clSize = it.getColumnIndex(MediaStore.Files.FileColumns.SIZE)
                val clDate = it.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED)
                var hasRow = it.moveToFirst()

                while (hasRow) {
                    val filePath = it.getString(clData)
                    val fileName = it.getString(clName)
                    val fileSize = it.getString(clSize)
                    val fileDate = it.getString(clDate)




                    if (fileName!=null){
                        apks.add(FileCustom(fileName,fileDate, fileSize,filePath))
                        Log.d(TAG, "Data  : $filePath")
                        Log.d(TAG, "Name : $fileName")
                        Log.d(TAG, "Size : $fileSize")
                        Log.d(TAG, "Date: $fileDate")
                    }


                    hasRow = it.moveToNext()
                }
            }
        } finally {
            cursor?.close()
        }
        return apks
    }

    fun formatDate(millis: Long?): String {
        if (millis== null){
          return "0"
        }
        else{
            val formater: DateFormat = SimpleDateFormat("dd.MMM.yyyy",Locale.US)
            return formater.format(Date(millis *1000))
        }

    }

    fun convertBytes(size: String?): String {
        if (size== null) {
            return "0b"
        } else {
            val units = arrayOf("B", "Kb", "Mb", "Gb", "Tb")
            val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
            return DecimalFormat("#,##0.#").format(
                size.toLong() / Math.pow(
                    1024.0,
                    digitGroups.toDouble()
                )
            ) + " " + units[digitGroups]


        }
    }

    suspend fun getListScreenShots(): ArrayList<String> = withContext(Dispatchers.Default) {
        val screenShots = ArrayList<String>()
        var arrFilePic = ArrayList<File>()
        var arrFileDCMI = ArrayList<File>()

        val filePic =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val fileDCMI = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)

        val screenshotsPic = File(filePic, "Screenshots")
        val screenshotsDCMI = File(fileDCMI, "Screenshots")

        Log.d(TAG, "getScreenShots: url: " + filePic.absolutePath)
        Log.d(TAG, "getScreenShots: url: " + fileDCMI?.absolutePath)
        Log.d(TAG, "getScreenShots: " + screenshotsPic.exists())
        if (screenshotsPic.exists()) {
            screenshotsPic.listFiles()?.let {
                arrFilePic = it.toCollection(ArrayList())
            }
        }
        if (screenshotsDCMI.exists()) {
            screenshotsDCMI.listFiles()?.let {
                arrFileDCMI = it.toCollection(ArrayList())
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
        screenShots
    }

    fun checkPathIsSDCard(path: String): Boolean {
        return !path.contains("/storage/emulated/0/")
    }

    fun deleteFile(context: Context, url: String, uri: Uri?): Boolean {
        if (checkPathIsSDCard(url)) {
            val documentFile: DocumentFile? = getDocumentSDCardFile(context, uri, url)
            return if (documentFile != null && documentFile.exists()) {
                documentFile.delete()
            } else false
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
                Log.d(TAG, "deleteImage: not esxits")
                false
            }
        }
    }

    fun galleryAddPic(context: Context, filePath: String) {
        val file = File(filePath)
        MediaScannerConnection.scanFile(
            context, arrayOf(file.toString()),
            null, null
        )
    }

    fun getDocumentSDCardFile(context: Context, rootDir: Uri?, path: String?): DocumentFile? {
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

    fun renameFile(context: Context, url: String, newName: String): Boolean {
        val oldFile = File(url)
        Log.d(TAG, "renameFile: old url: $url")
        val newPath = url.substring(0, url.lastIndexOf("/")) + "/" + newName

        Log.d(TAG, "renameFile: new url: $newPath")
        val newFile = File(newPath)
        if (oldFile.renameTo(newFile)) {
            galleryAddPic(context, newPath)
            return true
        }
        return false

    }

    fun renameFileSDCard(context: Context, url: String, newName: String) {
        val uri = getAvailabeAccessDirectoryUri(context, url)
        if (uri != null) {
            val documentFile: DocumentFile? = getDocumentSDCardFile(context, uri, url)
            if (documentFile != null && documentFile.exists()) {
                documentFile.renameTo(newName)
            }
        }
    }

    fun moveFileSDCard(context: Context, file: File, url: String) {
        var fileMove = File(url)
        var uri = getAvailabeAccessDirectoryUri(context, url)
        if (uri != null) {
            val documentFile: DocumentFile? = getDocumentSDCardFile(context, uri, url)
            if (documentFile != null && documentFile.exists()) {

                val newFile = File(fileMove, file.name)
                var outputChannel: FileChannel? = null
                var inputChannel: FileChannel? = null
                try {
                    outputChannel = FileOutputStream(newFile).channel
                    inputChannel = FileInputStream(file).channel
                    inputChannel.transferTo(0, inputChannel.size(), outputChannel)
                    inputChannel.close()
                    file.delete()
                } finally {
                    inputChannel?.close()
                    outputChannel?.close()
                }
            }
        }
    }

    fun getAccessDocumentFile(
        context: Context,
        rootDir: Uri?,
        path: String?
    ): DocumentFile? {
        val file = File(path)
        if (file.exists()) {
            val diretory = DocumentFile.fromTreeUri(context, rootDir!!)
            if (diretory == null || TextUtils.isEmpty(diretory.name)) {
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

    fun getAvailabeAccessDirectoryUri(context: Context, path: String?): Uri? {
        val uriPermissions = context.applicationContext.contentResolver.persistedUriPermissions
        if (uriPermissions.isEmpty()) {
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


    fun takeUriPermssion(context: Context, rootDir: Uri?) {
        val modeFlags =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        context.contentResolver.takePersistableUriPermission(rootDir!!, modeFlags)
    }

}

