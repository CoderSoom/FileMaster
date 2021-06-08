package com.android.filemaster.data.adapter

import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.data.model.FileDefault

class FileAdapter : BaseAdapter<FileDefault>(R.layout.item_file) {
    interface FileListener : BaseListener {
        fun onItemClick(position: Int)
    }
}