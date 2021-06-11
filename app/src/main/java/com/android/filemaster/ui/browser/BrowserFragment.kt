package com.android.filemaster.ui.browser

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentBrowserBinding
import com.android.filemaster.ui.home.ToolbarActionListener
import com.tapon.ds.view.toolbar.Toolbar

class BrowserFragment : BaseFragment<FragmentBrowserBinding>(), ToolbarActionListener {
    private val browseViewModel by viewModels<BrowserViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val browserAdapter = BrowserAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_browser
    }

    override fun getToolbar(): Toolbar {
        return binding.toolbarBrowser
    }

    override fun onBackPressed(): Boolean {
        backToHome()
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initData()
        this.observeViewModel()
    }

    private fun initData() {
        binding.toolbarBrowser.setOnToolbarActionListener(this)
        binding.adapter = browserAdapter
    }

    private fun observeViewModel() {
        binding.ivCheckAll.setOnClickListener { }
        binding.ivHome.setOnClickListener { }
        browserAdapter.listener = object : BrowserAdapter.BreadcrumbListener {
            override fun onItemBreadcrumbClicked(pathName: String, position: Int) {

            }
        }
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

    override fun onAction1Click() {
        val toolbarBrowser = binding.toolbarBrowser
        toolbarBrowser.activeInputType()
        toolbarBrowser.setVisibleAction1(false)
        toolbarBrowser.toolbar().setNavigationOnClickListener {
            toolbarBrowser.deactiveInputType()
            toolbarBrowser.setVisibleAction1(true)
            toolbarBrowser.toolbar()
                .setNavigationOnClickListener { backToHome() }
        }
    }

    override fun onAction2Click() {
    }

    override fun onTextChanged(text: String) {
    }

    override fun onTextInputCleared() {
    }
}