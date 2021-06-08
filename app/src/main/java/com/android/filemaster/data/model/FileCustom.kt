package com.android.filemaster.data.model

import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.adapter.SearchAdapter

data class FileCustom(val name: String?, val date: String?, val size: String?, val path: String?):
    BaseMultiViewHolderAdapter.BaseModelType(viewType = SearchAdapter.VIEW_TYPE_ITEM)
