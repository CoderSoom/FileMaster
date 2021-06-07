package com.android.filemaster.data.adapter

import com.android.filemaster.R
import com.android.filemaster.base.BaseListener
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.model.FileCustom

val resLayout = listOf<Int>(R.layout.item_date, R.layout.item_file_multi)

class RecentApdapter :
    BaseMultiViewHolderAdapter<BaseMultiViewHolderAdapter.BaseModelType>(resLayout) {
    companion object {
        const val VIEW_TYPE_DATE = 0
        const val VIEW_TYPE_ITEM = 1
    }

    interface IRecent : BaseListener {
       fun onClickItem(position:Int,item:FileCustom)
    }
}