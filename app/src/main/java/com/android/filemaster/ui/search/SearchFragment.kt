package com.android.filemaster.ui.search

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentSearchBinding
import com.android.filemaster.ui.home.ToolbarActionListener
import com.tapon.ds.view.toolbar.Toolbar

class SearchFragment : BaseFragment<FragmentSearchBinding>(), ToolbarActionListener {

    private val mainViewModel by activityViewModels<MainViewModel>()
    var searchAdapter = SearchAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    override fun getToolbar(): Toolbar {
        return binding.toolbarSearch
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initData()
        this.observeViewModel()
    }

    private fun initData() {
        binding.toolbarSearch.setOnToolbarActionListener(this)
        binding.toolbarSearch.activeInputType()
        binding.searchAdapter = searchAdapter
    }

    private fun observeViewModel() {
//        viewModel.recentMulti.observe(viewLifecycleOwner){
//            searchAdapter.list = it
//            Log.d("anhlt", "observeViewModel: "+it.toString())
//        }
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

    private fun backToHome() {
        mainViewModel.showMenu()
        findNavController().popBackStack(R.id.homeFragment, false)
    }

    override fun onBackPressed(): Boolean {
        backToHome()
        return true
    }

    override fun onTextChanged(text: String) {

    }

    override fun onTextInputCleared() {
    }

}