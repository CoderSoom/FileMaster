package com.android.filemaster.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.adapter.FileAdapter
import com.android.filemaster.data.model.FileDefault
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentHomeBinding
import com.android.filemaster.module.getAppColor
import com.android.filemaster.ui.customview.SpaceItemDecoation
import com.android.filemaster.utils.StartFileManager
import com.tapon.ds.view.toolbar.Toolbar
import java.io.File

class HomeFragment : BaseFragment<FragmentHomeBinding>(), ToolbarActionListener {
    private val viewModel by viewModels<HomeViewModel>()
    private val fileAdapter = FileAdapter()
    private val storageAdapter = StorageAdapter()
    private val recentAdapter = RecentHomeAdapter()
    private val mainViewModel by activityViewModels<MainViewModel>()
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getToolbar(): Toolbar {
        return binding.toolbarHome
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getListStorage(activityOwner)
        viewModel.getListAccess(activityOwner)
        viewModel.getListRecent(activityOwner)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        observeViewModel()
    }

    private fun initData() {
        binding.toolbarHome.setBackgroundColor(
            ContextCompat.getColor(
                activityOwner,
                R.color.transparent
            )
        )
        binding.toolbarHome.toolbar()
            .setTitleTextColor(ContextCompat.getColor(activityOwner, R.color.transparent))
        binding.toolbarHome.setOnToolbarActionListener(this)

        binding.viewModel = viewModel
        binding.fileAdapter = fileAdapter
        binding.storageAdapter = storageAdapter
        binding.recentAdapter = recentAdapter
        binding.rvListRecents.layoutManager =
            LinearLayoutManager(activityOwner, LinearLayoutManager.HORIZONTAL, false)
        binding.rvListRecents.isNestedScrollingEnabled = true

        val itemSpace = SpaceItemDecoation(resources.getDimension(R.dimen.px12))
        binding.rvListStorage.addItemDecoration(itemSpace)
    }

    private fun observeViewModel() {
        viewModel.liveCurrentFile.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.clNoAccess.visibility = View.VISIBLE
                binding.rvQuickAccess.visibility = View.GONE
            } else {
                binding.clNoAccess.visibility = View.GONE
                binding.rvQuickAccess.visibility = View.VISIBLE
                fileAdapter.list = it
            }
        }
        recentAdapter.listener = object : RecentHomeAdapter.RecentListener {
            override fun onClickItem(position: Int, item: FileDefault) {
                if (item.name.equals(getString(R.string.more)) && item.path == ".more") {
                    mainViewModel.hideMenu()
                    findNavController().navigate(R.id.action_homeFragment_to_recentsFragment)
                } else {
                    StartFileManager().openNomarlFile(activityOwner, File(item.path))
                }
            }
        }
        storageAdapter.listener = object : StorageAdapter.StorageListener {
            override fun onGDriveSync() {
                mainViewModel.hideMenu()
                findNavController().navigate(R.id.action_homeFragment_to_gdrive)
            }

            override fun onStorageClean() {
                mainViewModel.hideMenu()
                findNavController().navigate(R.id.action_homeFragment_to_cleaning)
            }

            override fun onGoStorage() {
                mainViewModel.hideMenu()
                findNavController().navigate(R.id.action_homeFragment_to_browse_file)
            }
        }


        viewModel.listFileRecentSingle.observe(viewLifecycleOwner) {

            if (it.size <= 4) {
                when (it.size) {
                    1 -> {
                        it.add(FileDefault("", "", "", "abc.xxx"))
                        it.add(FileDefault("", "", "", "abc.xxx"))
                        it.add(FileDefault("", "", "", "abc.xxx"))

                    }
                    2 -> {
                        it.add(FileDefault("", "", "", "abc.xxx"))
                        it.add(FileDefault("", "", "", "abc.xxx"))
                    }
                    3 -> {
                        it.add(FileDefault("", "", "", "abc.xxx"))
                    }
                }
                recentAdapter.list = it


            } else {
                val list = it.subList(0, 3)
                list.add(list.size, FileDefault(getString(R.string.more), "", "", ".more"))
                recentAdapter.list = list

            }
        }

        binding.tvExpand.setOnClickListener {
            viewModel.expanded(!viewModel.isExpanded.value!!)
        }

        binding.btnPremium.setOnClickListener {
            mainViewModel.hideMenu()
            findNavController().navigate(R.id.action_homeFragment_to_premium)
        }

        viewModel.listStorage.observe(viewLifecycleOwner) {
            storageAdapter.list = it
        }
    }

    override fun isDarkText(): Boolean {
        return false
    }

    override fun getStatusBarColor(): Int {
        return getAppColor(R.color.toolbar)
    }

    override fun onAction1Click() {
        mainViewModel.hideMenu()
        findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
    }

    override fun onAction2Click() {
        mainViewModel.hideMenu()
        findNavController().navigate(R.id.action_homeFragment_to_fileInsightFragment)
    }
}