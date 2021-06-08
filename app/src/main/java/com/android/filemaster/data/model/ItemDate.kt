package com.android.filemaster.data.model

import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.adapter.RecentApdapter
import com.android.filemaster.data.adapter.SearchAdapter


data class ItemDate(var date:String,var size:String):BaseMultiViewHolderAdapter.BaseModelType(viewType = SearchAdapter.VIEW_TYPE_DATE)