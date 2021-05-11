package com.android.filemaster.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    protected lateinit var mBinding: VB
    protected lateinit var mViewModel: VM

    abstract fun getClassViewModel(): Class<VM>
    abstract fun setBindingViewModel()
    abstract fun viewCreated()

    @LayoutRes
    abstract fun getLayoutId(): Int
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        this.setBindingViewModel()
        return this.mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewCreated()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mViewModel = activity?.run { ViewModelProvider(this).get(getClassViewModel()) }
                ?: throw Exception("Invalid Activity")
    }
}