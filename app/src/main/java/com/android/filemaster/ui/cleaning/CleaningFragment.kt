package com.android.filemaster.ui.cleaning

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentCleaningBinding

class CleaningFragment : BaseFragment<FragmentCleaningBinding>() {
    private val mainViewModel by activityViewModels<MainViewModel>()
    override fun getLayoutId(): Int {
        return R.layout.fragment_cleaning
    }

    override fun onBackPressed(): Boolean {
        backToHome()
        return true
    }

    private fun backToHome() {
        mainViewModel.showMenu()
        findNavController().popBackStack(R.id.homeFragment, false)
    }
}