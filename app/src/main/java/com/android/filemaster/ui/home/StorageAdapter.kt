package com.android.filemaster.ui.home

import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.data.model.ListStorage

class StorageAdapter : BaseAdapter<ListStorage>(R.layout.item_storage) {

    interface StorageListener : BaseListener {
        fun onItemClick(position: Int): ListStorage
        fun onGDriveSync()
    }
}