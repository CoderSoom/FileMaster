package com.android.filemaster.ui.recent

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.adapter.FileAdapterMulti
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.viewmodel.FileViewModel
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentRecentsBinding
import com.android.filemaster.ui.home.ToolbarActionListener
import com.tapon.ds.view.toolbar.Toolbar

class RecentsFragment : BaseFragment<FragmentRecentsBinding>(), ToolbarActionListener {
    private val viewModel by viewModels<FileViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val recentAdapter = RecentApdapter()
    private val TAG = "hhh"

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
        recentAdapter.listener = object : FileAdapterMulti.FileMultiListener {
            override fun onItemClick(
                item: BaseMultiViewHolderAdapter.BaseModelType
            ) {
                if (item is FileCustom) {
                    Log.d(TAG, "onItemClick: ${item.name}")
                }
            }

            override fun onLongClick(item: BaseMultiViewHolderAdapter.BaseModelType): Boolean {
                if (item is FileCustom && super.onLongClick(item)) {

                    return super.onLongClick(item)
                } else {
                    return false
                }
            }

            override fun onSelectClick() {
                val bottomSheet = MoreActionFragment()
                bottomSheet.show(childFragmentManager,bottomSheet.tag)
            }
        }
        binding.adapter = recentAdapter
        binding.rcListRecents.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

        binding.edtSearch.doAfterTextChanged {
            filter(it.toString())
        }
    }

    override fun onBackPressed(): Boolean {
        backToHome()
        return true
    }

    private fun observeViewModel() {
        viewModel.getListRecentForDay(activityOwner)
        viewModel.recentMulti.observe(viewLifecycleOwner) {
            recentAdapter.list = it
        }
    }

    private fun filter(str: String) {
    }

    private fun backToHome() {
        mainViewModel.showMenu()
        findNavController().popBackStack(R.id.homeFragment, false)
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

    override fun onAction2Click() {
        binding.toolbarRecent.activeInputType()
        binding.toolbarRecent.toolbar().setNavigationOnClickListener {
            binding.toolbarRecent.deactiveInputType()
            binding.toolbarRecent.toolbar()
                .setNavigationOnClickListener { backToHome() }
        }
    }

    override fun onTextChanged(text: String) {
    }

    override fun onTextInputCleared() {
    }
}