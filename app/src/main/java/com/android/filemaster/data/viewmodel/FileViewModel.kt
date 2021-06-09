package com.android.filemaster.data.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.model.*
import com.android.filemaster.data.repository.FileRepository
import com.android.filemaster.module.asLiveData
import kotlinx.coroutines.launch
import java.util.*

class FileViewModel() : ViewModel() {

    private val TAG = "FileViewModel"
    private val fileRepository = FileRepository()

    val liveAllFile = MutableLiveData<List<FileDefault>>()
    val liveCurrentFile = MutableLiveData<List<FileDefault>>()

    private val _listStorage = MutableLiveData<MutableList<ListStorage>>()
    val listStorage = _listStorage.asLiveData()

    private val _listSearch = MutableLiveData<MutableList<FileCustom>>()
    val listSearch = _listSearch.asLiveData()

    private val _listFileRecentSingLe = MutableLiveData<MutableList<FileDefault>>()
    val listFileRecentSingle = _listFileRecentSingLe.asLiveData()

    private val _listFileRecentMulti = MutableLiveData<MutableList<FileDefault>>()
    val listFileRecentMulti = _listFileRecentSingLe.asLiveData()

    private val _listFileAccess = MutableLiveData<MutableList<FileDefault>>()
    val listFileAccess = _listFileAccess.asLiveData()

    val recentMulti = MutableLiveData<MutableList<BaseMultiViewHolderAdapter.BaseModelType>>()

    val isExpanded by lazy {
        MutableLiveData(false)
    }


    fun getListSearch(ctx: Context){
        viewModelScope.launch {
            val result = fileRepository.getListSearch(ctx)
            _listSearch.postValue(result)
        }
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
//                if (result.isEmpty()) {
//                    liveCurrentFile.value = result
//                } else liveCurrentFile.value = result.subList(0, 5)
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


    fun getListStorage(ctx: Context){
        viewModelScope.launch {
            val result = fileRepository.getListStorage(ctx)
            _listStorage.postValue(result)
        }
    }

    fun getListRecentForDay(context: Context) {
        val recents = mutableListOf<BaseMultiViewHolderAdapter.BaseModelType>()
        var countToday = 0
        var countThisWeek = 0
        var indexOfThisWeek = 0
        val list = fileRepository.getListFileRecentMulti(context)
        recents.addAll(list)
        run loop@{
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)

            recents.forEachIndexed { index, item ->
                if ((item as FileCustom).date!!.toLong() > cal.timeInMillis / 1000) {
                    countToday++
                } else {
                    countThisWeek = recents.size - countToday
                    indexOfThisWeek = index + 1
                    recents.add(0, ItemDate("Today", countToday.toString()))
                    return@loop
                }

            }

        }
        recents.add(indexOfThisWeek, ItemDate("The week", countThisWeek.toString()))
        recentMulti.value = recents
    }

}