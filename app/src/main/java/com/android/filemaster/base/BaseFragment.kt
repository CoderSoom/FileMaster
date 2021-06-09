package com.android.filemaster.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.android.filemaster.ui.main.MainActivity
import com.tapon.ds.view.toolbar.Toolbar

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {
    protected lateinit var binding: VB

    private lateinit var myInflater: LayoutInflater
    private lateinit var callback: OnBackPressedCallback

    protected val activityOwner by lazy {
        requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::myInflater.isInitialized) {
            myInflater = LayoutInflater.from(context)
        }
        binding = DataBindingUtil.inflate(myInflater, getLayoutId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initToolbar()
        initBinding()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (getStatusBarColor() != null && isDarkText() != null) {
            setStatusColor(getStatusBarColor()!!, isDarkText()!!)
        }
        super.onViewCreated(view, savedInstanceState)
        setBackPressedDispatcher()
    }

    abstract fun getLayoutId(): Int

    private fun setBackPressedDispatcher() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        activityOwner.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    open fun onBackPressed() {}

    private fun initToolbar() {
        val toolbar = this.getToolbar() ?: return
        val parentActivity = activity as AppCompatActivity
        setHasOptionsMenu(true)
        parentActivity.setSupportActionBar(toolbar.toolbar())
    }

    open fun getToolbar(): Toolbar? {
        return null
    }

    open fun initBinding() {}

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val toolbar = getToolbar()
        if (toolbar != null && activity != null) {
            toolbar.onCreateOptionsMenu(activity as AppCompatActivity, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val toolbar = getToolbar()
        if (toolbar != null) {
            return toolbar.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

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