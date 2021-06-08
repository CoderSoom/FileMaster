package com.android.filemaster.module

import android.app.Application
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.model.ListStorage
import com.android.filemaster.ui.customview.CircularProgressBar
import com.android.filemaster.utils.FileManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

private var appInstance: Application? = null

fun Application.initBaseApplication() {
    appInstance = this
}

fun getApplication() = appInstance!!

@BindingAdapter("rv_set_adapter")
fun <T : RecyclerView.ViewHolder> RecyclerView.applyAdapter(applyAdapter: RecyclerView.Adapter<T>?) {
    applyAdapter?.apply {
        adapter = applyAdapter
    }
}

@BindingAdapter("tv_get_detail")
fun TextView.getDetailFile(item: FileCustom) {
    this.text = item.size + " | " + FileManager.formatDate(item.date)
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

@BindingAdapter("setImage")
fun setImage(img: ImageView, path: String) {
    if (FileManager.setImageFile(path) == 1) {
        Glide.with(img)
            .load(Uri.fromFile(File(path)))
            .apply( RequestOptions().override(100, 100))
            .into(img)
    } else {
        img.setImageResource(FileManager.setImageFile(path))
    }
}

fun getAppColor(@ColorRes colorRes: Int, context: Context? = appInstance) =
    context?.let { ContextCompat.getColor(it, colorRes) } ?: Color.TRANSPARENT




@BindingAdapter("setImageDrawable")
fun setImageDrawable(img: ImageView, path: Int) {
    img.setImageResource(path)
}

@BindingAdapter("setProgressbar")
fun CircularProgressBar.setProgressbar(item: ListStorage) {
    if (item.totalMemorySize !=null && item.amountOfMemoryUsed!=null) {
        this.setProgress(item.amountOfMemoryUsed!!.toInt(), item.totalMemorySize!!.toInt())
    }else{
    }
}

@BindingAdapter("setVisibleStorage")
fun CircularProgressBar.setVisibleStorage(item: ListStorage) {
    if (item.nameStorage != "Storage") {
        this.visibility = View.INVISIBLE
    }
}

@BindingAdapter("setVisibleImages")
fun ImageView.setVisibleImages(item: ListStorage) {
    if (item.nameStorage != "Storage") {
        this.visibility = View.VISIBLE
    }
}

