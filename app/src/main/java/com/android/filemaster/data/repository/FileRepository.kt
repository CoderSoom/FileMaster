package com.android.filemaster.data.repository

import android.content.Context
import com.android.filemaster.utils.FileManager

class FileRepository {
    fun getListFileRecentMulti(context: Context) =
        FileManager.getListRecentMulti(context)
    fun getListFileRecentSingle(context: Context) =
        FileManager.getListRecentSingle(context)

    fun getListAccess(context: Context) = FileManager.getListAccess(context)

}