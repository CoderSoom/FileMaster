package com.android.filemaster.ui.fileinsight

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentFileInsightBinding

class FileInsightFragment : BaseFragment<FragmentFileInsightBinding>() {
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_file_insight
    }

    override fun onBackPressed(): Boolean {
        mainViewModel.showMenu()
        findNavController().popBackStack(R.id.homeFragment, false)
        return true
    }

}