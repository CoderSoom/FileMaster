package com.android.filemaster.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.filemaster.base.BaseViewModel
import com.android.filemaster.data.model.FileDefault
import com.android.filemaster.data.model.ListStorage
import com.android.filemaster.data.repository.FileRepository
import com.android.filemaster.module.asLiveData
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val fileRepository = FileRepository()

    val liveCurrentFile = MutableLiveData<List<FileDefault>>()

    private val _listStorage = MutableLiveData<MutableList<ListStorage>>()
    val listStorage = _listStorage.asLiveData()

    private val _listFileRecentSingLe = MutableLiveData<MutableList<FileDefault>>()
    val listFileRecentSingle = _listFileRecentSingLe.asLiveData()

    private val _listFileAccess = MutableLiveData<MutableList<FileDefault>>()
    val listFileAccess = _listFileAccess.asLiveData()

    val isExpanded by lazy {
        MutableLiveData(false)
    }

    fun getListRecent(ctx: Context) {
        viewModelScope.launch {
            val result = fileRepository.getListFileRecentSingle(ctx)
            _listFileRecentSingLe.postValue(result)
        }
    }

    fun getListAccess(ctx: Context) {
        viewModelScope.launch {
            val result = fileRepository.getListAccess(ctx)
            _listFileAccess.postValue(result)
            if (isExpanded.value == false) {
                if (result.size <= 5) {
                    liveCurrentFile.value = result
                } else liveCurrentFile.value = result.subList(0, 5)
            }
        }
    }

    fun expanded(enable: Boolean) {
        isExpanded.value = enable
        if (isExpanded.value == false) {
            val result = _listFileAccess.value?.subList(0, 5)
            liveCurrentFile.value = result?.toMutableList()
        } else {
            val result = _listFileAccess.value
            liveCurrentFile.value = result?.toMutableList()
        }
    }


    fun getListStorage(ctx: Context) {
        viewModelScope.launch {
            val result = fileRepository.getListStorage(ctx)
            _listStorage.postValue(result)
        }
    }

}