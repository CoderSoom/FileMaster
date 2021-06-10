package com.android.filemaster.data.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.repository.FileRepository
import com.android.filemaster.module.asLiveData
import kotlinx.coroutines.launch

class FileViewModel() : ViewModel() {

    private val TAG = "FileViewModel"
    private val fileRepository = FileRepository()

    private val _listSearch = MutableLiveData<MutableList<FileCustom>>()
    val listSearch = _listSearch.asLiveData()

    fun getListSearch(ctx: Context) {
        viewModelScope.launch {
            val result = fileRepository.getListSearch(ctx)
            _listSearch.postValue(result)
        }
    }


}