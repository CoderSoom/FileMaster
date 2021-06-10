package com.android.filemaster.ui.recent

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.adapter.FileAdapterMulti
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.model.ItemAction
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentRecentsBinding
import com.android.filemaster.ui.home.ToolbarActionListener
import com.tapon.ds.view.toolbar.Toolbar

class RecentsFragment : BaseFragment<FragmentRecentsBinding>(), ToolbarActionListener {
    private val TAG = "RecentsFragment"
    private val viewModel by viewModels<RecentViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val recentAdapter = RecentApdapter()
    private val actionAdapter = ActionSelectAdapter()
    override fun getLayoutId(): Int {
        return R.layout.fragment_recents
    }

    override fun getToolbar(): Toolbar {
        return binding.toolbarRecent
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        observeViewModel()
    }

    private fun initData() {
        binding.toolbarRecent.setOnToolbarActionListener(this)
        recentAdapter.listener = object : FileAdapterMulti.FileMultiListener {
            override fun onItemClick(
                item: BaseMultiViewHolderAdapter.BaseModelType
            ) {
                if (item is FileCustom) {
                    Log.d(TAG, "onItemClick: ${item.name}")
                }
            }

            override fun onLongClick(item: BaseMultiViewHolderAdapter.BaseModelType): Boolean {
                if (item is FileCustom && super.onLongClick(item)) {
                    binding.clRcBottom.visibility = View.VISIBLE
                    viewModel.isSelect.postValue(true)
                    return super.onLongClick(item)
                } else {
                    viewModel.isSelect.postValue(false)
                    return false
                }
            }

            override fun onSelectClick(item: BaseMultiViewHolderAdapter.BaseModelType) {
                if (item is FileCustom) {
                    Log.d(TAG, "onSelectClick: ${item.name}")
                    val bottomSheet = MoreActionFragment().newInstance(item)
                    bottomSheet.show(childFragmentManager, bottomSheet.tag)
                }

            }

        }
        binding.adapter = recentAdapter
        binding.rcListRecents.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )
        binding.rcActionSelect.adapter = actionAdapter
        binding.rcActionSelect.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        )
        val list = arrayListOf<ItemAction>()
        list.add(ItemAction(getString(R.string.move_to), R.drawable.ic_action_move_to))
        list.add(ItemAction(getString(R.string.copy_to), R.drawable.ic_action_copy))
        list.add(ItemAction(getString(R.string.share), R.drawable.ic_action_share))
        list.add(ItemAction(getString(R.string.trash), R.drawable.ic_action_trash))
        list.add(ItemAction(getString(R.string.more), R.drawable.ic_action_more))
        actionAdapter.list = list
        actionAdapter.listener = object : ActionSelectAdapter.IAction {
            override fun onItemClick(itemAction: ItemAction) {
                when (itemAction.name) {
                    getString(R.string.move_to) -> {

                    }
                    getString(R.string.copy_to) -> {
                    }
                    getString(R.string.share) -> {
                    }
                    getString(R.string.share) -> {
                    }
                    getString(R.string.more) -> {

                    }
                }
            }

        }
    }

    override fun onBackPressed(): Boolean {
        backToHome()
        return true
    }

    private fun observeViewModel() {
        val list = viewModel.getListRecentFromStorage(activityOwner)
        viewModel.mappingListRecentForDay(list)
        viewModel.recentMulti.observe(viewLifecycleOwner) {
            recentAdapter.list = it
        }
    }


    private fun backToHome() {
        mainViewModel.showMenu()
        findNavController().popBackStack(R.id.homeFragment, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                backToHome()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }

    override fun onAction2Click() {
        binding.toolbarRecent.activeInputType()
        binding.toolbarRecent.toolbar().setNavigationOnClickListener {
            binding.toolbarRecent.deactiveInputType()
            binding.toolbarRecent.toolbar()
                .setNavigationOnClickListener { backToHome() }
        }
    }

    override fun onTextChanged(text: String) {
        val list = viewModel.search(text)
        viewModel.mappingListRecentForDay(list)
    }

    override fun onTextInputCleared() {
    }
}