package com.android.filemaster.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.filemaster.utils.FileManager

class FileRepository {
    fun getListFileRecent(context: Context, number: Int) =
        FileManager.getListRecent(context, number)

    fun getListAccess(context: Context) = FileManager.getListAccess(context)

}