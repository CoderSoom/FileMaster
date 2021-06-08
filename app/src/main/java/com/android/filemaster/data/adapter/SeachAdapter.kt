package com.android.filemaster.data.adapter

import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.base.BaseViewHolder
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.model.ListSeach
import com.android.filemaster.databinding.ItemExpandableRecentBinding
import com.android.filemaster.model.ItemFileRecent


val resLayout = listOf(R.layout.item_title, R.layout.item_file_multi)

class SearchAdapter :
    BaseMultiViewHolderAdapter<BaseMultiViewHolderAdapter.BaseModelType>(resLayout) {
    companion object {
        const val VIEW_TYPE_DATE = 0
        const val VIEW_TYPE_ITEM = 1
    }

    interface ISearch : BaseListener {
        fun onClickItem(position:Int,item: FileCustom)
    }
}