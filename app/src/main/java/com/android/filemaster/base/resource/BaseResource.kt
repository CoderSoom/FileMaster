package com.android.filemaster.base.resource

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class BaseResource {

    private var context: Context
    constructor(ctx: Context) {
        this.context = ctx
    }

    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    fun getImageResouce(resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    fun getColor(resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }
}