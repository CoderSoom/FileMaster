package com.android.filemaster.base.adapter

import com.android.filemaster.R
import com.android.filemaster.model.FileDefault

class RecentsAdaper : BaseAdapter<FileDefault>(R.layout.item_file_recents) {
    interface IRecents : BaseAdapter.ListItemListener {
        fun onClickItem(position: Int): FileDefault
    }

}