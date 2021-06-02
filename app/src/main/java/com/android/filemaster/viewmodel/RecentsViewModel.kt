package com.android.filemaster.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.filemaster.base.BaseViewModel
import com.android.filemaster.model.FileDefault
import com.android.filemaster.utils.FileManager

class RecentsViewModel:BaseViewModel {
    lateinit var data :MutableLiveData<MutableList<FileDefault>>
    constructor(){
        data = MutableLiveData()
    }
    fun getData(context: Context){
        data .postValue(FileManager.getListRecent(context,10))
    }
}