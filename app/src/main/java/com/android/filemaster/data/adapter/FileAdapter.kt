package com.android.filemaster.data.adapter

import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.data.model.FileCustom

class FileAdapter : BaseAdapter<FileCustom>(R.layout.item_file_multi) {

    interface FileListener : BaseListener {
        fun onItemClick(position: Int, item: FileCustom)
    }

}