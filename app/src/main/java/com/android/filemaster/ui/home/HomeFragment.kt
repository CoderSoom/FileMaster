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
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.model.ItemDate
import com.android.filemaster.data.viewmodel.FileViewModel
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentHomeBinding
import com.android.filemaster.module.getAppColor
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

    override fun getToolbar(): Toolbar? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getListFake()
        viewModel.getListStorage()
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

        //        binding.progressStorage.setProgress(totalMemorySize, amountOfMemoryUsed)
//        binding.tvUsedStorage.text= getFileSize(amountOfMemoryUsed) +" / " +getFileSize(totalMemorySize)
//        binding.tvUsed.text = (getUsedStorage()+" USED")

//        Toast.makeText(requireActivity(), getUsedStorage()l, Toast.LENGTH_SHORT).show()


    }

    private fun observeViewModel() {
        viewModel.liveCurrentFile.observe(viewLifecycleOwner) {
            fileAdapter.list = it
        }
        recentAdapter.listener = object : RecentHomeAdapter.RecentListener {
            override fun onClickItem(position: Int, item: FileCustom) {
                if (item.name.equals(getString(R.string.more)) && item.date.equals("")) {
                    mainViewModel.hideMenu()
                    findNavController().navigate(R.id.action_homeFragment_to_recentsFragment)
                }
            }
        }
        viewModel.listFileRecent.observe(viewLifecycleOwner) {
            Log.d(TAG, "observeViewModel: $it")
            if (it.size <= 4) {
                recentAdapter.list = it
                when (it.size) {
                    1 -> {
                        for (i in it.size - 1..0) {
                            it.add(i, FileCustom("", "", "", "abc.xxx"))
                        }
                    }
                    2 -> {
                        for (i in it.size - 1..0) {
                            it.add(i, FileCustom("", "", "", "abc.xxx"))
                        }
                    }
                    3 -> {
                        it.add(FileCustom("", "", "", "abc.xxx"))
                    }
                }

            } else {
                val list = it.subList(0, 3)
                list.add(list.size, FileCustom(getString(R.string.more), "", "", ".more"))
                recentAdapter.list = list

            }

        }
        recentAdapter.listener = object :RecentHomeAdapter.RecentListener{
            override fun onClickItem(position: Int, item: FileCustom) {
                if (item.name.equals("More") && item.path.equals(".more")){
                    findNavController().navigate(R.id.action_homeFragment_to_recentsFragment)
                }
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