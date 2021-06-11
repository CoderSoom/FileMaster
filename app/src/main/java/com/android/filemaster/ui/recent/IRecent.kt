package com.android.filemaster.ui.recent

import android.view.View
import com.android.filemaster.base.BaseListener
import com.android.filemaster.base.BaseMultiViewHolderAdapter

interface IRecent : BaseListener {
    fun onItemClick(position:Int, item: BaseMultiViewHolderAdapter.BaseModelType)
    fun onLongClick(position:Int,item: BaseMultiViewHolderAdapter.BaseModelType)=true
    fun onSelectClick(position:Int,item: BaseMultiViewHolderAdapter.BaseModelType)
    fun isCheckClick(view:View)
}