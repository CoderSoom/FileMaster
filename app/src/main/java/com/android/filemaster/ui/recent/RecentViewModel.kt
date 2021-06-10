package com.android.filemaster.ui.recent

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.base.BaseViewModel
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.model.ItemDate
import com.android.filemaster.data.repository.FileRepository
import com.android.filemaster.module.asLiveData
import java.util.*

class RecentViewModel() : BaseViewModel() {
    companion object {
        private const val TAG = "RecentViewModel"
    }

    private val fileRepository = FileRepository()

    private val _listSearch = MutableLiveData<MutableList<FileCustom>>()
    val listSearch = _listSearch.asLiveData()

    private val _listFileRecentMulti = MutableLiveData<MutableList<FileCustom>>()
    val listFileRecentMulti = _listFileRecentMulti.asLiveData()

    val recentMulti = MutableLiveData<MutableList<BaseMultiViewHolderAdapter.BaseModelType>>()

    private val listFileRecentFull = MutableLiveData<List<FileCustom>>()
    val listFileRecentCurrent = MutableLiveData<List<FileCustom>>()

    val isSelect by lazy {
         MutableLiveData(false)
    }

    fun getListRecentFromStorage(context: Context): List<FileCustom> {
        val list = fileRepository.getListFileRecentMulti(context)
        listFileRecentFull.value = list
        return list
    }

    fun search(char: String): List<FileCustom> {
        val result = mutableListOf<FileCustom>()
        listFileRecentFull.value?.forEachIndexed { index, item ->
            if (item.name?.lowercase()?.contains(char.lowercase()) == true) {
                result.add(item)
            }
        }
        return result
    }

    fun mappingListRecentForDay(list: List<FileCustom>) {
        val recents = mutableListOf<BaseMultiViewHolderAdapter.BaseModelType>()
        var countToday = 0
        var countThisWeek = 0
        var indexOfThisWeek = 0
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
                    indexOfThisWeek = if (countToday > 0) {
                        index + 1
                    } else {
                        index
                    }
                    return@loop
                }
            }
        }
        if (countToday > 0) {
            recents.add(0, ItemDate("Today", countToday.toString()))
        }
        if (countThisWeek > 0) {
            recents.add(indexOfThisWeek, ItemDate("The week", countThisWeek.toString()))
        }
        recentMulti.value = recents
    }

}