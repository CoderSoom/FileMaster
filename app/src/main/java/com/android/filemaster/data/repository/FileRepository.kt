package com.android.filemaster.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.filemaster.utils.FileManager
import java.io.File

class FileRepository {
    fun getListFileRecent(context: Context) =
        FileManager.getListRecent(context)

    fun getListAccess(context: Context) = FileManager.getListAccess(context)


    fun getListStorage(context: Context) = FileManager.getListStorage(context)
}