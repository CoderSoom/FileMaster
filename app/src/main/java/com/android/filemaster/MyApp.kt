package com.android.filemaster

import com.android.filemaster.base.BaseApplication
import com.android.filemaster.base.resource.BaseResource
import com.android.filemaster.module.initBaseApplication

class MyApp : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        initBaseApplication()
    }
}