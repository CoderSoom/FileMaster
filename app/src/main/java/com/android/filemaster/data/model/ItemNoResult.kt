package com.android.filemaster.data.model

import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.utils.Constant

data class ItemNoResult(var str: String) :
    BaseMultiViewHolderAdapter.BaseModelType(viewType = Constant.VIEW_TYPE_NULL)