package com.android.filemaster.ui.cloud.gdrive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentGdriveBinding

class GDriveFragment : BaseFragment<FragmentGdriveBinding>() {
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_gdrive
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