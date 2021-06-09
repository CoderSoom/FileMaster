package com.android.filemaster.ui.recent

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.data.model.ItemAction
import com.android.filemaster.databinding.BottomSheetFragmentMoreActionBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MoreActionFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetFragmentMoreActionBinding
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var list: ArrayList<ItemAction>
    val TAG = "xx"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomsheet = super.onCreateDialog(savedInstanceState)
        val view = View.inflate(context, R.layout.bottom_sheet_fragment_more_action, null)
        binding = DataBindingUtil.bind(view)!!
        bottomsheet.setContentView(view)
        val adapter = ActionAdapter()
        binding.rcAction.adapter = adapter
        binding.rcAction.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        init()
        adapter.list = list
        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.setPeekHeight(resources.getDimension(R.dimen.px220).toInt())
        return bottomsheet
    }

    private fun init() {
        list = arrayListOf()
        list.add(ItemAction("Move to", R.drawable.ic_more))
        list.add(ItemAction("Move to", R.drawable.ic_more))
        list.add(ItemAction("Move to", R.drawable.ic_more))
        list.add(ItemAction("Move to", R.drawable.ic_more))
        list.add(ItemAction("Move to", R.drawable.ic_more))
        list.add(ItemAction("Move to", R.drawable.ic_more))
        list.add(ItemAction("Move to", R.drawable.ic_more))
        list.add(ItemAction("Move to", R.drawable.ic_more))
        list.add(ItemAction("Move to", R.drawable.ic_more))
        list.add(ItemAction("Move to", R.drawable.ic_more))
        list.add(ItemAction("Move to", R.drawable.ic_more))
        list.add(ItemAction("Move to", R.drawable.ic_more))

    }


}