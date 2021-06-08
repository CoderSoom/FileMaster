package com.android.filemaster.data.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.model.FileDefault
import com.android.filemaster.data.model.ItemDate
import com.android.filemaster.data.model.ListStorage
import com.android.filemaster.data.repository.FileRepository
import com.android.filemaster.module.asLiveData
import com.android.filemaster.utils.DataFake
import kotlinx.coroutines.launch
import java.util.*

class FileViewModel() : ViewModel() {

    private val TAG = "FileViewModel"
    private val fileRepository = FileRepository()

    private val _listFileRecentSingLe = MutableLiveData<MutableList<FileDefault>>()
    val listFileRecentSingle = _listFileRecentSingLe.asLiveData()

    private val _listFileRecentMulti = MutableLiveData<MutableList<FileDefault>>()
    val listFileRecentMulti = _listFileRecentSingLe.asLiveData()

    private val _listFileAccess = MutableLiveData<MutableList<FileCustom>>()
    val listFileAccess = _listFileAccess.asLiveData()

    val liveAllFile = MutableLiveData<List<FileDefault>>()
    val liveCurrentFile = MutableLiveData<List<FileDefault>>()

    val recentMulti = MutableLiveData<MutableList<BaseMultiViewHolderAdapter.BaseModelType>>()
    val listStorge = MutableLiveData<List<ListStorage>>()

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
        }
    }

    fun getListFake() {
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


    fun getListStorage() {
        listStorge.value = DataFake.listStorage
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
                Log.d(TAG, "getListRecentForDay: " + list.size)

            }

        }
        recents.add(indexOfThisWeek, ItemDate("The week", countThisWeek.toString()))
        Log.d(TAG, "getListRecentForDay: " + recents.toString())
        recentMulti.value = recents
    }


}