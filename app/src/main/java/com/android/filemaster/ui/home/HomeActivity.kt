package com.android.filemaster.ui.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.filemaster.R
import com.android.filemaster.base.BaseActivity
import com.android.filemaster.databinding.ActivityHomeBinding
import javax.inject.Inject

class HomeActivity @Inject constructor(private val context: Context) :
    BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId() = R.layout.activity_home

    override fun getViewModel() = HomeViewModel::class.java

    override fun init() {

    }
}