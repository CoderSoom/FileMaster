package com.android.filemaster.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.filemaster.R
import com.android.filemaster.base.BaseActivity
import com.android.filemaster.databinding.ActivityHomeBinding

class HomeActivity :BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId() = R.layout.activity_home
}