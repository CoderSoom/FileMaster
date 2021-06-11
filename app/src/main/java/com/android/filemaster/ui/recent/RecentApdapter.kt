package com.android.filemaster.ui.recent

import com.android.filemaster.R
import com.android.filemaster.base.BaseListener
import com.android.filemaster.base.BaseMultiViewHolderAdapter

val resLayout = listOf(R.layout.item_date, R.layout.item_file_multi)

class RecentApdapter :
    BaseMultiViewHolderAdapter<BaseMultiViewHolderAdapter.BaseModelType>(resLayout) {
    interface IRecent : BaseListener {
        fun onSelectAllItem(item: BaseMultiViewHolderAdapter.BaseModelType)
    }
}