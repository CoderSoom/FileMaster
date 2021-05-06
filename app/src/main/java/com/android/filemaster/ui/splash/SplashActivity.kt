package com.android.filemaster.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.filemaster.R
import com.android.filemaster.base.BaseActivity
import com.android.filemaster.base.BaseViewModel
import com.android.filemaster.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId() = R.layout.activity_splash

    override fun getViewModel() = SplashViewModel::class.java
    override fun setViewModel() {
    }
}