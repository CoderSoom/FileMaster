package com.android.filemaster.ui.recent

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.model.ItemAction
import com.android.filemaster.databinding.BottomSheetFragmentMoreActionBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MoreActionFragment : BottomSheetDialogFragment() {
    private val TAG = "MoreActionFragment"
    private val KEY_FILE_CUSTOM = "FILE_CUSTOM"
    private lateinit var binding: BottomSheetFragmentMoreActionBinding
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var list: ArrayList<ItemAction>
    private lateinit var fileCustom: FileCustom
    private lateinit var actionAdapter: ActionAdapter


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomsheet = super.onCreateDialog(savedInstanceState)
        val view = View.inflate(context, R.layout.bottom_sheet_fragment_more_action, null)
        binding = DataBindingUtil.bind(view)!!
        bottomsheet.setContentView(view)
//        actionAdapter = ActionAdapter(object :ActionAdapter.IAction{
//            override fun onItemClickAction() {
//                Log.d(TAG, "onItemClick: ")
//
//            }
//
//        })
        actionAdapter = ActionAdapter()
        binding.adapter = actionAdapter
        binding.rcAction.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        init()
        actionAdapter.list = list
        actionAdapter.listener = object : ActionAdapter.IAction {
            override fun onItemClickAction() {
                Log.d(TAG, "onItemClick: ")

            }
        }
        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.setPeekHeight(resources.getDimension(R.dimen.px220).toInt())
        return bottomsheet
    }

    fun newInstance(item: FileCustom): MoreActionFragment {
        val bottomSheetFragment = MoreActionFragment()
        val bundle = Bundle()
        bundle.putSerializable(KEY_FILE_CUSTOM, item)
        bottomSheetFragment.arguments = bundle
        return bottomSheetFragment
    }


    private fun init() {
        list = arrayListOf()
        list.add(ItemAction(getString(R.string.move_to), R.drawable.ic_action_move_to))
        list.add(ItemAction(getString(R.string.copy_to), R.drawable.ic_action_copy))
        list.add(ItemAction(getString(R.string.share), R.drawable.ic_action_share))
        list.add(ItemAction(getString(R.string.trash), R.drawable.ic_action_trash))
        list.add(ItemAction(getString(R.string.open_with), R.drawable.ic_action_open_with))
        list.add(ItemAction(getString(R.string.rename), R.drawable.ic_action_rename))
        list.add(ItemAction(getString(R.string.compress), R.drawable.ic_action_compress))
        list.add(ItemAction(getString(R.string.extract), R.drawable.ic_action_extract))
        list.add(ItemAction(getString(R.string.move_to_safe), R.drawable.ic_action_safe))
        list.add(ItemAction(getString(R.string.add_to_workspace), R.drawable.ic_action_work_space))
        list.add(
            ItemAction(
                getString(R.string.add_to_quick_access),
                R.drawable.ic_action_quick_access
            )
        )
        list.add(ItemAction(getString(R.string.upload_to_cloud), R.drawable.ic_action_cloud_upload))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundleReceive = arguments
        if (bundleReceive != null) {
            fileCustom = bundleReceive.get(KEY_FILE_CUSTOM) as FileCustom
            Log.d(TAG, "onCreate: ${fileCustom.name}")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setClick()
    }

    private fun setClick() {
//        actionAdapter.listener = object : ActionAdapter.IAction {
//            override fun onItemClick(itemAction: ItemAction) {
//                Log.d(TAG, "onItemClick: 1")
//
//                when (itemAction.name) {
//                    getString(R.string.move_to) -> {
//                        Log.d(TAG, "onItemClick: 1")
//                    }
//                    getString(R.string.copy_to) -> {
//
//                    }
//                    getString(R.string.share) -> {
//
//                    }
//                    getString(R.string.trash) -> {
//
//                    }
//                    getString(R.string.open_with) -> {
//
//                    }
//                    getString(R.string.rename) -> {
//
//                    }
//                    getString(R.string.compress) -> {
//
//                    }
//                    getString(R.string.extract) -> {
//
//                    }
//                    getString(R.string.move_to_safe) -> {
//
//                    }
//                    getString(R.string.add_to_workspace) -> {
//
//                    }
//                    getString(R.string.add_to_quick_access) -> {
//
//                    }
//                    getString(R.string.upload_to_cloud) -> {
//                        Log.d(TAG, "onItemClick: 1")
//                    }
//
//                }
//            }
//
//        }
    }
}