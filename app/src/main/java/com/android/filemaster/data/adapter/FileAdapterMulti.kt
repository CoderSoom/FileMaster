package com.android.filemaster.data.adapter

import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.base.BaseViewHolder
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.databinding.ItemFileMultiBinding

class FileAdapterMulti : BaseAdapter<FileCustom>(R.layout.item_file_multi) {

    interface FileMultiListener : BaseListener {
        fun onItemClick(item: BaseMultiViewHolderAdapter.BaseModelType)
        fun onLongClick(item: BaseMultiViewHolderAdapter.BaseModelType)=true
        fun onSelectClick()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = holder.binding as ItemFileMultiBinding
    }

}