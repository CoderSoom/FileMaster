package com.android.filemaster.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.databinding.FragmentBrowserBinding
import com.android.filemaster.databinding.FragmentMenuBinding
import com.android.filemaster.viewmodel.BrowserViewModel
import com.android.filemaster.viewmodel.MenuViewModel


class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>() {
    override fun getClassViewModel(): Class<MenuViewModel> {
      return MenuViewModel::class.java
    }

    override fun setBindingViewModel() {
    }

    override fun viewCreated() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_menu
    }


}