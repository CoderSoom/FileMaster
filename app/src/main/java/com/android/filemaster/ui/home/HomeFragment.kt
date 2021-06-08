package com.android.filemaster.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.adapter.FileAdapter
import com.android.filemaster.data.adapter.FileAdapterMulti
import com.android.filemaster.data.adapter.RecentHomeAdapter
import com.android.filemaster.data.adapter.StorageAdapter
import com.android.filemaster.data.model.FileDefault
import com.android.filemaster.data.viewmodel.FileViewModel
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentHomeBinding
import com.android.filemaster.module.getAppColor
import com.tapon.ds.view.toolbar.Toolbar

class HomeFragment : BaseFragment<FragmentHomeBinding>(), ToolbarActionListener {
    private val viewModel by viewModels<FileViewModel>()
    private val fileAdapter = FileAdapter()
    private val fileAdapterRecent = FileAdapterMulti()
    private val storageAdapter = StorageAdapter()
    private val recentAdapter = RecentHomeAdapter()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val TAG = "HomeFragment"
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    val activityOwner by lazy {
        requireActivity() as MainActivity
    }

    override fun getToolbar(): Toolbar {
        return binding.toolbarHome
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getListStorage()
        viewModel.getListAccess(requireActivity())
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

        //        binding.progressStorage.setProgress(totalMemorySize, amountOfMemoryUsed)
//        binding.tvUsedStorage.text= getFileSize(amountOfMemoryUsed) +" / " +getFileSize(totalMemorySize)
//        binding.tvUsed.text = (getUsedStorage()+" USED")

//        Toast.makeText(requireActivity(), getUsedStorage()l, Toast.LENGTH_SHORT).show()

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
                if (item.name.equals(getString(R.string.more)) && item.path.equals(".more")) {
                    mainViewModel.hideMenu()
                    findNavController().navigate(R.id.action_homeFragment_to_recentsFragment)
                } else {
//                    StartFileManager().openNomarlFile(requireContext(), item)
                    Toast.makeText(activityOwner, "Update soon", Toast.LENGTH_LONG).show()
                }
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

        viewModel.listStorge.observe(viewLifecycleOwner) {
//             if (it){
//
//             }
//             storageAdapter.list = it
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