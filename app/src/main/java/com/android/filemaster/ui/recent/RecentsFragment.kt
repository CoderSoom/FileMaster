package com.android.filemaster.ui.recent

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.adapter.FileAdapterMulti
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.viewmodel.FileViewModel
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentRecentsBinding
import com.android.filemaster.ui.home.ToolbarActionListener
import com.tapon.ds.view.toolbar.OnToolbarActionListener
import com.tapon.ds.view.toolbar.Toolbar

class RecentsFragment : BaseFragment<FragmentRecentsBinding>(), ToolbarActionListener {
    private val TAG = "RecentsFragment"

    private val viewModel by viewModels<FileViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val recentAdapter = RecentApdapter()

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
        binding.adapter = recentAdapter
        binding.rcListRecents.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )
        val fileApdapter = FileAdapterMulti()
        fileApdapter.listener = object : FileAdapterMulti.FileListener {
            override fun onItemClick(position: Int, item: FileCustom) {
                val bottomSheet = MoreActionFragment()
                bottomSheet.show(childFragmentManager, tag)
            }

        }

//        binding.btnSearch.setOnClickListener {
//            binding.searchLayout.visibility = View.VISIBLE
//            binding.recents.visibility = View.GONE
//            binding.edtSearch.requestFocus()
//        }
//
//        binding.btnSearchClose.setOnClickListener {
//            binding.searchLayout.visibility = View.GONE
//            binding.recents.visibility = View.VISIBLE
//            hideSoftKeyboard(it)
//        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

        })
    }

    override fun onBackPressed() {
        Log.d(TAG, "onBackPressed: ")
        mainViewModel.showMenu()
        findNavController().popBackStack(R.id.homeFragment, false)
    }

    private fun observeViewModel() {
        viewModel.getListRecentForDay(activityOwner)
        viewModel.recentMulti.observe(viewLifecycleOwner) {
            recentAdapter.list = it
        }
    }

    private fun filter(str: String) {
    }

    private fun hideSoftKeyboard(view: View) {
        val imm =
            activityOwner.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                mainViewModel.showMenu()
                findNavController().popBackStack(R.id.homeFragment, false)
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }

    override fun onAction2Click() {

    }
}