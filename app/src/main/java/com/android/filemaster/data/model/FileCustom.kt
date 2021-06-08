package com.android.filemaster.data.model

import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.adapter.RecentApdapter

data class FileCustom(val name: String?, val date: String?, val size: String?, val path: String?) :
    BaseMultiViewHolderAdapter.BaseModelType(viewType = RecentApdapter.VIEW_TYPE_ITEM)
