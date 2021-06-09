package com.android.filemaster.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.viewmodel.FileViewModel
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val viewModel by viewModels<FileViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    var searchAdapter = SearchAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getListSearch(activityOwner)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchAdapter = searchAdapter
        observeViewModel()
    }

    private fun observeViewModel() {
//        viewModel.recentMulti.observe(viewLifecycleOwner){
//            searchAdapter.list = it
//            Log.d("anhlt", "observeViewModel: "+it.toString())
//        }
    }

    override fun onBackPressed() {
        mainViewModel.showMenu()
        findNavController().popBackStack(R.id.homeFragment, false)
    }

}