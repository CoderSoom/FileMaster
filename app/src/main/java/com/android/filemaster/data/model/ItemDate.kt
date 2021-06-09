package com.android.filemaster.data.model

import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.adapter.RecentApdapter

data class ItemDate(var date:String,var size:String):BaseMultiViewHolderAdapter.BaseModelType(viewType = RecentApdapter.VIEW_TYPE_DATE)