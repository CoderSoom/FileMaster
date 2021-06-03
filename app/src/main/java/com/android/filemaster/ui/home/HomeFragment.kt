package com.android.filemaster.ui.home

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.adapter.FileAdapter
import com.android.filemaster.data.adapter.RecentAdapter
import com.android.filemaster.data.viewmodel.FileViewModel
import com.android.filemaster.databinding.FragmentHomeBinding
import com.android.filemaster.module.getAppColor

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel by viewModels<FileViewModel>()
    private val fileAdapter = FileAdapter()
    private val recentAdapter = RecentAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getList()
//        viewModel.getListAccess(requireActivity())
        viewModel.getListRecent(requireActivity(), 10)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.fileAdapter = fileAdapter

        binding.recentAdapter = recentAdapter
        binding.rvListRecents.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvListRecents.isNestedScrollingEnabled = true

        binding.progressStorage.setProgress(75)
        binding.progressStorage.setProcessBig(100)

        binding.moreRecent.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_recentsFragment)
        }
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = requireActivity().window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.liveCurrentFile.observe(viewLifecycleOwner) {
            fileAdapter.list = it
        }

        viewModel.listFileRecent.observe(viewLifecycleOwner) {
            if (it.size <= 4) {
                recentAdapter.list = it
                binding.moreRecent.visibility = View.INVISIBLE
            } else {
                recentAdapter.list = it.subList(0, 3)
                binding.moreRecent.visibility = View.VISIBLE
                binding.moreRecent.setOnClickListener {

                }
            }
        }

        binding.tvExpand.setOnClickListener {
            viewModel.expanded(!viewModel.isExpanded.value!!)
        }
    }

    override fun isDarkText(): Boolean {
        return false
    }

    override fun getStatusBarColor(): Int {
        return getAppColor(R.color.toolbar)
    }


}