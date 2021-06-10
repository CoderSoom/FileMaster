package com.android.filemaster.ui.browser

import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.databinding.FragmentFilesBinding


class FilesFragment : BaseFragment<FragmentFilesBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_files
    }
}