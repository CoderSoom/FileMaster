package com.android.filemaster.data.adapter

import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.model.ListStorage
import com.android.filemaster.model.ListStorageMin

class StorageAdapter : BaseAdapter<ListStorage>(R.layout.item_strorage) {

    interface FileListener : BaseListener {
        fun onItemClick(position: Int, item: FileCustom)
    }
}