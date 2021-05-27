package com.android.filemaster.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected lateinit var mBinding:VB
    protected lateinit var mViewModel: VM

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun getViewModel(): Class<VM>
    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = ViewModelProvider(this).get(getViewModel())
        this.init()
    }

    protected fun goToActivity(activity: Class<*>, key: String?, bundle: Bundle?) {
        val intent = Intent(this, activity)
        if (bundle != null) {
            intent.putExtra(key, bundle)
        }
        this.startActivity(intent)
    }

    protected fun goToActivityWithClearTask(activity: Class<*>) {
        val intent = Intent(this, activity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        this.startActivity(intent)
    }
}