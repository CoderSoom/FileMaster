package com.android.filemaster.ui.cloud.gdrive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.databinding.FragmentGdriveBinding

class GDriveFragment : BaseFragment<FragmentGdriveBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_gdrive
    }
}