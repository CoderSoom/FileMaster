package com.android.filemaster.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import com.android.filemaster.R
import com.android.filemaster.base.BaseActivity
import com.android.filemaster.databinding.ActivitySplashBinding
import com.android.filemaster.ui.home.HomeActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    private val TAG: String? = "testAAAA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId() = R.layout.activity_splash

    override fun getViewModel() = SplashViewModel::class.java
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun init() {
        Handler().postDelayed(Runnable {
         var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }, 1000)

    }
}