package com.android.filemaster.data.adapter

import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.base.BaseViewHolder
import com.android.filemaster.databinding.ItemExpandableRecentBinding
import com.android.filemaster.model.FileDefault
import com.android.filemaster.model.ItemFileRecent

class RecentApdapter:BaseAdapter<ItemFileRecent>(R.layout.item_expandable_recent){
    interface IExpandableRecent: BaseListener{
        fun onItemClick(position:Int):FileDefault
        fun onItemLongClick(position: Int):FileDefault
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = holder.binding as ItemExpandableRecentBinding
        val adapter = FileAdapter()
        item.rcData.adapter =adapter
        adapter.list = list!![position].list

    }

}