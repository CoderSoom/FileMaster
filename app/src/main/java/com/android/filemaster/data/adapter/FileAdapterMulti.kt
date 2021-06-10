package com.android.filemaster.data.adapter

import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.model.FileCustom

class FileAdapterMulti : BaseAdapter<FileCustom>(R.layout.item_file_multi) {

    interface FileMultiListener : BaseListener {
        fun onItemClick(item: BaseMultiViewHolderAdapter.BaseModelType)
        fun onLongClick(item: BaseMultiViewHolderAdapter.BaseModelType)=true
        fun onSelectClick(item: BaseMultiViewHolderAdapter.BaseModelType)
    }
}