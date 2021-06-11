package com.android.filemaster.ui.cloud.gdrive

import androidx.lifecycle.MutableLiveData
import com.android.filemaster.base.BaseViewModel
import com.android.filemaster.utils.AppPrefs

class GDriveViewModel : BaseViewModel() {

    val isIntegratedCloud: Boolean
        get() = AppPrefs.instance!!.isIntegratedGDriveCloud

}