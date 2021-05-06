package com.android.filemaster

import com.android.filemaster.base.BaseApplication
import com.android.filemaster.base.resource.BaseResource

class MyApp : BaseApplication() {
    companion object{
        private lateinit var resource: BaseResource
        fun getResource() = resource
    }
    override fun onCreate() {
        super.onCreate()
        resource = BaseResource(ctx = applicationContext)
    }
}