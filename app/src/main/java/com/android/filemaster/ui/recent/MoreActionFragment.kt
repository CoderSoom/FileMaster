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
        list.add(ItemAction(getString(R.string.move_to), R.drawable.ic_action_move))
        list.add(ItemAction(getString(R.string.copy_to), R.drawable.ic_action_copy))
        list.add(ItemAction(getString(R.string.share), R.drawable.ic_action_share))
        list.add(ItemAction(getString(R.string.trash), R.drawable.ic_action_trash))
        list.add(ItemAction(getString(R.string.open_with), R.drawable.ic_action_open_with))
        list.add(ItemAction(getString(R.string.rename), R.drawable.ic_action_rename))
        list.add(ItemAction(getString(R.string.compress), R.drawable.ic_action_compress))
        list.add(ItemAction(getString(R.string.extract), R.drawable.ic_action_extract))
        list.add(ItemAction(getString(R.string.move_to_safe), R.drawable.ic_action_safe))
        list.add(ItemAction(getString(R.string.add_to_workspace), R.drawable.ic_action_work_space))
        list.add(ItemAction(getString(R.string.add_to_quick_access), R.drawable.ic_action_quick_access))
        list.add(ItemAction(getString(R.string.upload_to_cloud), R.drawable.ic_action_cloud_upload))

    }


}