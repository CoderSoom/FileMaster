package com.android.filemaster.data.repository

import android.content.Context
import com.android.filemaster.utils.FileManager
import java.io.File

class FileRepository {
    fun getListFileRecentMulti(context: Context) =
        FileManager.getListRecentMulti(context)
    fun getListFileRecentSingle(context: Context) =
        FileManager.getListRecentSingle(context)

    fun getListAccess(context: Context) = FileManager.getListAccess(context)


    fun getListStorage(context: Context) = FileManager.getListStorage(context)

    fun getListSearch(context: Context) = FileManager.getListSearch(context, "12")
}