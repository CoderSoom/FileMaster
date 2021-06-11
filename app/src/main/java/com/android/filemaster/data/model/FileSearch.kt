package com.android.filemaster.data.model

import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.utils.Constant

data class FileSearch( var topicName: String, var listFiles: MutableList<FileCustom>):
    BaseMultiViewHolderAdapter.BaseModelType(viewType = Constant.VIEW_TYPE_ITEM)
{
}