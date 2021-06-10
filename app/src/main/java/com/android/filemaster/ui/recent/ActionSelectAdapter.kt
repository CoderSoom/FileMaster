package com.android.filemaster.ui.recent

import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.data.model.ItemAction

class ActionSelectAdapter: BaseAdapter<ItemAction>(R.layout.item_action_select_more) {
    interface IAction:BaseListener{
        fun onItemClick(itemAction: ItemAction)
    }
}