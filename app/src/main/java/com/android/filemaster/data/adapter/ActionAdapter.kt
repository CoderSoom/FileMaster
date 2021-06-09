package com.android.filemaster.data.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.base.BaseViewHolder
import com.android.filemaster.data.model.ItemAction
import com.android.filemaster.databinding.ItemActionBinding


class ActionAdapter : BaseAdapter<ItemAction>(R.layout.item_action) {
    interface IAction : BaseListener

    val TAG = "hh"
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = holder.binding as ItemActionBinding

        Log.d(TAG, "onBindViewHolder: " + position)
        if (position % 4 == 0 && position != 0) {
            item.viewLine.visibility = View.VISIBLE
        } else {
            item.viewLine.visibility = View.GONE
        }

    }
}