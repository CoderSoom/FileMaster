package com.android.filemaster.data.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.filemaster.module.asLiveData

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _liveShowMenu by lazy {
        MutableLiveData(true)
    }

    fun showMenu() {
        _liveShowMenu.value = true
    }

    fun hideMenu() {
        Log.d(TAG, "hideMenu: ")
        _liveShowMenu.value = false
    }

    fun getLiveMenu() = _liveShowMenu.asLiveData()
}