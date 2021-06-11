package com.android.filemaster.data.model

import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.utils.Constant

data class ItemDate(var date: String, var size: String?) :
    BaseMultiViewHolderAdapter.BaseModelType(viewType = Constant.VIEW_TYPE_DATE)