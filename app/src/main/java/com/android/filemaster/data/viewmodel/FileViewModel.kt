package com.android.filemaster.data.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.repository.FileRepository
import com.android.filemaster.module.asLiveData
import com.android.filemaster.utils.DataFake
import kotlinx.coroutines.launch

class FileViewModel() : ViewModel() {

    private val fileRepository = FileRepository()

    private val _listFileRecent = MutableLiveData<MutableList<FileCustom>>()
    val listFileRecent = _listFileRecent.asLiveData()

    private val _listFileAccess = MutableLiveData<MutableList<FileCustom>>()
    val listFileAccess = _listFileAccess.asLiveData()

    val liveAllFile = MutableLiveData<List<FileCustom>>()
    val liveCurrentFile = MutableLiveData<List<FileCustom>>()

    val isExpanded by lazy {
        MutableLiveData(false)
    }


    fun getListRecent(ctx: Context, number: Int) {
        viewModelScope.launch {
            val result = fileRepository.getListFileRecent(ctx, number)
            _listFileRecent.postValue(result)
        }
    }

    fun getListAccess(ctx: Context) {
        viewModelScope.launch {
            val result = fileRepository.getListAccess(ctx)
            _listFileAccess.postValue(result)
        }
    }

    fun getList() {
        val result = DataFake.list
        liveAllFile.value = result
        if (isExpanded.value == false) {
            liveCurrentFile.value = result.subList(0, 5)
        }
    }

    fun expanded(enable: Boolean) {
        isExpanded.value = enable
        if (isExpanded.value == false) {
            val result = liveAllFile.value?.subList(0, 5)
            liveCurrentFile.value = result?.toMutableList()
        } else {
            val result = liveAllFile.value
            liveCurrentFile.value = result?.toMutableList()
        }
    }


}