package com.android.filemaster.ui.recent

import com.android.filemaster.R
import com.android.filemaster.base.BaseMultiViewHolderAdapter

val resLayout = listOf(R.layout.item_date, R.layout.item_file_multi, R.layout.item_no_result)

class RecentApdapter :
    BaseMultiViewHolderAdapter<BaseMultiViewHolderAdapter.BaseModelType>(resLayout) {

}