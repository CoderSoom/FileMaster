package com.android.filemaster.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {
    protected lateinit var binding: VB

    private lateinit var myInflater: LayoutInflater


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (!::myInflater.isInitialized) {
            myInflater = LayoutInflater.from(context)
        }
        binding = DataBindingUtil.inflate(myInflater, getLayoutId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initBinding()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (getStatusBarColor() != null && isDarkText() != null) {
            setStatusColor(getStatusBarColor()!!, isDarkText()!!)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun getLayoutId(): Int

    open fun initBinding() {}

    private fun setStatusColor(color: Int = Color.BLACK, state: Boolean = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = activity?.window
            window?.let { w ->
                w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                var newUiVisibility = w.decorView.systemUiVisibility
                newUiVisibility = if (state) {
                    newUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    newUiVisibility and (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR).inv()
                }
                w.decorView.systemUiVisibility = newUiVisibility
                w.statusBarColor = color
            }
        }
    }

    open fun getStatusBarColor(): Int? {
        return null
    }

    open fun isDarkText(): Boolean? {
        return null
    }
}