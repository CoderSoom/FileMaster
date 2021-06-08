package com.android.filemaster.ui.customview

import android.graphics.Rect
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoation(val verticalSpaceHeight: Float) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = verticalSpaceHeight.toInt()
        } else if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
            outRect.right = verticalSpaceHeight.toInt()
        }
    }
}