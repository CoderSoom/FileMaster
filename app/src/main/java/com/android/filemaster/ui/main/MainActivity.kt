package com.android.filemaster.ui.main

import android.content.Context
import android.os.Bundle
import com.android.filemaster.R
import com.android.filemaster.base.BaseActivity
import com.android.filemaster.databinding.ActivityMainBinding
import com.android.filemaster.ui.home.HomeViewModel
import javax.inject.Inject


class MainActivity @Inject constructor(private val context: Context) :
    BaseActivity<ActivityMainBinding, HomeViewModel>() {
    private val TAG: String? = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun getViewModel() = HomeViewModel::class.java

    override fun init() {
    }


}