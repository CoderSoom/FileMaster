package com.android.filemaster.ui.search

import com.android.filemaster.R
import com.android.filemaster.base.BaseListener
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.model.FileCustom

class SearchAdapter :
    BaseMultiViewHolderAdapter<BaseMultiViewHolderAdapter.BaseModelType>(
        listOf(
            R.layout.item_title,
            R.layout.item_file_multi
        )
    ) {

    interface ISearch : BaseListener {
        fun onClickItem(position: Int, item: FileCustom)
    }
}