package com.android.filemaster.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.adapter.RecentApdapter
import com.android.filemaster.data.viewmodel.FileViewModel
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentRecentsBinding
import com.android.filemaster.model.ItemFileRecent

class RecentsFragment : BaseFragment<FragmentRecentsBinding>() {
    private val TAG = "RecentsFragment"

    private val viewModel by viewModels<FileViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val recentAdapter = RecentApdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_recents
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getListRecentForDay(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        observeViewModel()
    }

    private fun initData() {
        binding.adapter = recentAdapter
        binding.rcListRecents.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

        binding.btnBack.setOnClickListener {
            mainViewModel.showMenu()
            requireActivity().onBackPressed()
        }

        binding.btnSearch.setOnClickListener {
            binding.searchLayout.visibility = View.VISIBLE
            binding.recents.visibility = View.GONE
            binding.edtSearch.requestFocus()
        }

        binding.btnSearchClose.setOnClickListener {
            binding.searchLayout.visibility = View.GONE
            binding.recents.visibility = View.VISIBLE
            hideSoftKeyboard(it)
        }

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

    private fun observeViewModel() {
        viewModel.listFileRecentForDay.observe(viewLifecycleOwner) {
            recentAdapter.list = it
        }
    }

    private fun filter(str: String) {
    }

    private fun hideSoftKeyboard(view: View) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}