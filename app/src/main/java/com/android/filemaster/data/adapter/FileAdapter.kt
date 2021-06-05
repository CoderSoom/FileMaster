package com.android.filemaster.data.adapter

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.base.BaseViewHolder
import com.android.filemaster.data.model.FileCustom

class FileAdapter : BaseAdapter<FileCustom>(R.layout.item_file) {

    interface FileListener : BaseListener {
        fun onItemClick(position: Int, item: FileCustom)
    }

}