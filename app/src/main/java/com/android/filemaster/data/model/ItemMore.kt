package com.android.filemaster.data.model

import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.utils.Constant
import java.io.Serializable

data  class ItemMore(var name: String, var censored: String, var image: Int)  :
    BaseMultiViewHolderAdapter.BaseModelType(viewType = Constant.VIEW_TYPE_ITEM), Serializable