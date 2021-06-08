package com.android.filemaster.data.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.model.ListStorage
import com.android.filemaster.data.repository.FileRepository
import com.android.filemaster.model.ItemFileRecent
import com.android.filemaster.module.asLiveData
import com.android.filemaster.utils.DataFake
import kotlinx.coroutines.launch
import java.util.*

class FileViewModel() : ViewModel() {

    private val fileRepository = FileRepository()

    private val _listFileRecent = MutableLiveData<MutableList<FileCustom>>()
    val listFileRecent = _listFileRecent.asLiveData()

    private val _listFileAccess = MutableLiveData<MutableList<FileCustom>>()
    val listFileAccess = _listFileAccess.asLiveData()

    val liveAllFile = MutableLiveData<List<FileCustom>>()
    val liveCurrentFile = MutableLiveData<List<FileCustom>>()

    private val _listFileRecentForDay = MutableLiveData<MutableList<ItemFileRecent>>()
    val listFileRecentForDay = _listFileRecentForDay.asLiveData()


    private val _listStorage = MutableLiveData<MutableList<ListStorage>>()
    val listStorage = _listStorage.asLiveData()

    private val todayFileDefault = mutableListOf<FileCustom>()
    private val weekFileDefault = mutableListOf<FileCustom>()
    private val today = mutableListOf<ItemFileRecent>()

    val isExpanded by lazy {
        MutableLiveData(false)
    }


    fun getListRecent(ctx: Context) {
        viewModelScope.launch {
            val result = fileRepository.getListFileRecent(ctx)
            _listFileRecent.postValue(result)
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

    fun getListRecentForDay(ctx: Context) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        for (recent in fileRepository.getListFileRecent(ctx)) {
            if (recent.date!!.toLong() > cal.timeInMillis / 1000) {
                todayFileDefault.add(recent)
            } else {
                weekFileDefault.add(recent)
            }
        }
        today.add(
            ItemFileRecent(
                "Today",
                "(" + todayFileDefault.size.toString() + " File)",
                todayFileDefault
            )
        )
        today.add(
            ItemFileRecent(
                "The Week",
                "(" + weekFileDefault.size.toString() + " File)",
                weekFileDefault
            )
        )
        _listFileRecentForDay.postValue(today)
    }

    fun getListStorage(ctx: Context){
        viewModelScope.launch {
            val result = fileRepository.getListStorage(ctx)
            _listStorage.postValue(result)
        }
    }


}