package com.android.filemaster.ui.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.base.BaseViewModel
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.model.FileSearch
import com.android.filemaster.data.model.ItemDate
import com.android.filemaster.data.repository.FileRepository
import java.io.File
import kotlin.math.log

class SearchViewModel : BaseViewModel() {
    companion object {
        private const val TAG = "anhlt"
    }

    private val fileRepository = FileRepository()


    val searchAll = MutableLiveData<MutableList<BaseMultiViewHolderAdapter.BaseModelType>>()
    private val listFileSearchFull = MutableLiveData<List<FileSearch>>()

//    fun getListSeach(context: Context): List<FileSearch> {
//        val list = fileRepository.getListSearch(context)
//        listFileSearchFull.value = list
//        return list
//    }


    val resultImg = mutableListOf<FileCustom>()
    var resultAudio = mutableListOf<FileCustom>()
    var resultVideo = mutableListOf<FileCustom>()

    fun search(keySeach: String): List<FileSearch> {
        var result = mutableListOf<FileSearch>()
        for (file in listFileSearchFull.value!![0].listFiles) {
            if (file.name?.lowercase()!!.contains(keySeach.lowercase())) {
                resultImg.add(file)
            }
        }
        for (file in listFileSearchFull.value!![1].listFiles) {
            if (file.name?.lowercase()!!.contains(keySeach.lowercase())) {
                resultAudio.add(file)
            }
        }
        for (file in listFileSearchFull.value!![2].listFiles) {
            if (file.name?.lowercase()!!.contains(keySeach.lowercase())) {
                resultVideo.add(file)
            }
        }
        result.add(FileSearch("img", resultImg))
        result.add(FileSearch("audio", resultAudio))
        result.add(FileSearch("video", resultVideo))
        return result

    }

    fun setUpListSearch(list: List<FileSearch>) {


    }


}