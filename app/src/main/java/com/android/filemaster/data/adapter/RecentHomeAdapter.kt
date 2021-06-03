package com.android.filemaster.data.adapter

import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.data.model.FileCustom

class RecentHomeAdapter : BaseAdapter<FileCustom>(R.layout.item_file_recents) {
    interface RecentListener : BaseListener {
        fun onClickItem(position: Int): FileCustom
    }

}