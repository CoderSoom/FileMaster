package com.android.filemaster.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.filemaster.base.BaseViewModel
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.model.FileDefault
import com.android.filemaster.utils.FileManager

class RecentsViewModel : BaseViewModel {
     var data: MutableLiveData<MutableList<FileCustom>>

    constructor() {
        data = MutableLiveData()
    }

    fun getData(context: Context) {
        data.postValue(FileManager.getListRecent(context))
    }
}