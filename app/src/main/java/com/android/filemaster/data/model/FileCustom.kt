package com.android.filemaster.data.model

import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.utils.Constant

data class FileCustom(val name: String?, val date: String?, val size: String?, val path: String?) :
    BaseMultiViewHolderAdapter.BaseModelType(viewType = Constant.VIEW_TYPE_ITEM)
