package com.android.filemaster.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.adapter.SearchAdapter
import com.android.filemaster.data.viewmodel.FileViewModel
import com.android.filemaster.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val viewModel by viewModels<FileViewModel>()
    var searchAdapter = SearchAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       viewModel.getListSeachAll(requireActivity())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchAdapter = searchAdapter
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.recentMulti.observe(viewLifecycleOwner){
            searchAdapter.list = it
            Log.d("anhlt", "observeViewModel: "+it.toString())
        }
    }

}