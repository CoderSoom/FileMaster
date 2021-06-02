package com.android.filemaster.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object Utils {
    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(img:ImageView,path:String){
        img.setImageResource(FileManager.setImageFile(path))
    }
}