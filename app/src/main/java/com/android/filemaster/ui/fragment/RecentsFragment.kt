package com.android.filemaster.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.databinding.FragmentMenuBinding
import com.android.filemaster.databinding.FragmentRecentsBinding
import com.android.filemaster.viewmodel.MenuViewModel
import com.android.filemaster.viewmodel.RecentsViewModel

class RecentsFragment : BaseFragment<FragmentRecentsBinding, RecentsViewModel>() {
    override fun getClassViewModel(): Class<RecentsViewModel> {
      return RecentsViewModel::class.java
    }

    override fun setBindingViewModel() {
    }

    override fun viewCreated() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_recents
    }


}