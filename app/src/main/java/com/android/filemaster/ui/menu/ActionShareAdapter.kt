package com.android.filemaster.ui.menu

import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.data.model.ItemAction
import com.android.filemaster.data.model.ListStorage
import java.text.FieldPosition

class ActionShareAdapter : BaseAdapter<ItemAction>(R.layout.item_file_m_list) {
    interface IShare : BaseListener {
        fun onItemClick(position: Int, itemAction: ItemAction)
    }

}