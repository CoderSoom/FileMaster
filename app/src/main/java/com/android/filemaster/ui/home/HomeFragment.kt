package com.android.filemaster.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.adapter.FileAdapter
import com.android.filemaster.data.adapter.RecentHomeAdapter
import com.android.filemaster.data.adapter.StorageAdapter
import com.android.filemaster.data.viewmodel.FileViewModel
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentHomeBinding
import com.android.filemaster.module.getAppColor
import com.android.filemaster.ui.customview.SpaceItemDecoation
import com.tapon.ds.view.toolbar.Toolbar

class HomeFragment : BaseFragment<FragmentHomeBinding>(), ToolbarActionListener {
    private val viewModel by viewModels<FileViewModel>()
    private val fileAdapter = FileAdapter()
    private val storageAdapter = StorageAdapter()
    private val recentAdapter = RecentHomeAdapter()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val TAG = "HomeFragment"
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getToolbar(): Toolbar {
        return binding.toolbarHome
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getListFake()
        viewModel.getListStorage(requireActivity())
//        viewModel.getListAccess(requireActivity())
        viewModel.getListRecent(requireActivity())


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()

        observeViewModel()

    }

    private fun initData() {
        binding.toolbarHome.setBackgroundColor(
            ContextCompat.getColor(
                this.requireContext(),
                R.color.transparent
            )
        )
        binding.toolbarHome.setOnToolbarActionListener(this)

        binding.viewModel = viewModel
        binding.fileAdapter = fileAdapter

        binding.storageAdapter = storageAdapter

        binding.recentAdapter = recentAdapter
        binding.rvListRecents.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvListRecents.isNestedScrollingEnabled = true

        binding.moreRecent.setOnClickListener {
            mainViewModel.hideMenu()
            findNavController().navigate(R.id.action_homeFragment_to_recentsFragment)
        }
        val itemSpace = SpaceItemDecoation(resources.getDimension(R.dimen.px10))
        binding.rvListStorage.addItemDecoration(itemSpace)
    }

    private fun observeViewModel() {
        viewModel.liveCurrentFile.observe(viewLifecycleOwner) {
            fileAdapter.list = it
        }

        viewModel.listFileRecent.observe(viewLifecycleOwner) {
            Log.d(TAG, "observeViewModel: $it")
            if (it.size <= 4) {
                recentAdapter.list = it
                binding.moreRecent.visibility = View.INVISIBLE
            } else {
                recentAdapter.list = it.subList(0, 3)
                binding.moreRecent.visibility = View.VISIBLE
            }
        }
        binding.tvExpand.setOnClickListener {
            viewModel.expanded(!viewModel.isExpanded.value!!)
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
        TODO("Not yet implemented")
    }
}