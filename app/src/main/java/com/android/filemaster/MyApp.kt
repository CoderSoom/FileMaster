package com.android.filemaster

import android.annotation.SuppressLint
import com.android.filemaster.base.BaseApplication
import com.android.filemaster.base.resource.BaseResource
import com.android.filemaster.module.initBaseApplication
import com.android.filemaster.utils.AppPrefs

class MyApp : BaseApplication() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var resource: BaseResource
        fun resource() = resource
    }

    override fun onCreate() {
        super.onCreate()
        resource = BaseResource(ctx = applicationContext)
        this.init()
    }

    private fun init() {
        initBaseApplication()
        AppPrefs.init(this)
    }
}