package com.android.filemaster.ui.browser

import com.android.filemaster.R
import com.android.filemaster.base.BaseAdapter
import com.android.filemaster.base.BaseListener
import com.android.filemaster.data.model.BreadcrumbItem

class BrowserAdapter : BaseAdapter<BreadcrumbItem>(R.layout.item_browser_breadcrumb) {
    interface BreadcrumbListener : BaseListener {
        fun onItemBreadcrumbClicked(pathName: String, position: Int)
    }
}