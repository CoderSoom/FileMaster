package com.android.filemaster.ui.menu

import com.android.filemaster.R
import com.android.filemaster.base.BaseListener
import com.android.filemaster.base.BaseMultiViewHolderAdapter

val resLayout = listOf(R.layout.item_file_header_with_title, R.layout.item_file_l_meta_list)

class ActionMoreByInnosoftAdapter :
    BaseMultiViewHolderAdapter<BaseMultiViewHolderAdapter.BaseModelType>(resLayout) {
    interface IMore : BaseListener {
    }
}