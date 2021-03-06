package com.android.filemaster.module

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
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
import com.android.filemaster.R
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.model.*
import com.android.filemaster.utils.FileManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.tapon.ds.view.toolbar.Toolbar
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

@SuppressLint("SetTextI18n")
@BindingAdapter("tv_get_detail")
fun TextView.getDetailFile(item: FileDefault) {
    this.text =
        FileManager.convertBytes(item.size?.toLong()) + " | " + FileManager.formatDate(item.date?.toLong())
}

@SuppressLint("SetTextI18n")
@BindingAdapter("getDetailFileMulti")
fun TextView.getDetailFileMulti(item: BaseMultiViewHolderAdapter.BaseModelType) {
    if (item is FileCustom) {
        this.text =
            FileManager.convertBytes(item.size?.toLong()) + " | " + FileManager.formatDate(item.date?.toLong())
    }
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

@BindingAdapter("setImage")
fun setImage(img: ImageView, path: String) {
    if (FileManager.setImageFile(path) == 1) {
        Glide.with(img)
            .load(Uri.fromFile(File(path)))
            .apply(RequestOptions().override(100, 100))
            .error(R.drawable.ic_file_image)
            .into(img)
    } else {
        img.setImageResource(FileManager.setImageFile(path))
    }
}


@BindingAdapter("tv_set_text_by_multi_holder")
fun TextView.tvSetTextByMultiHolder(item: BaseMultiViewHolderAdapter.BaseModelType) {
    if (item is ItemDate) {
        text = item.date
    }
}

@BindingAdapter("tv_set_text_by_multi_holder_file")
fun TextView.tvSetTextByMultiHolderFile(item: BaseMultiViewHolderAdapter.BaseModelType) {
    if (item is FileCustom) {
        text = item.name
    }
}

@BindingAdapter("tv_no_result")
fun TextView.tvNoResultByMultiHolder(item: BaseMultiViewHolderAdapter.BaseModelType) {
    if (item is ItemNoResult) {
        text = item.str
    }
}

@BindingAdapter("setImageMulti")
fun ImageView.setImageMulti(item: BaseMultiViewHolderAdapter.BaseModelType) {
    if (item is FileCustom) {
        if (FileManager.setImageFile(item.path.toString()) == 1) {
            Glide.with(this)
                .load(Uri.fromFile(File(item.path.toString())))
                .apply(RequestOptions().override(100, 100))
                .error(R.drawable.ic_file_image)
                .into(this)
        } else {
            this.setImageResource(FileManager.setImageFile(item.path.toString()))
        }
    }
}


@BindingAdapter("setProgressBar")
fun setProgressBar(circularProcessbar: CircularProgressBar, process: Float?) {
    if (process != null) {
        circularProcessbar.progress = process
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
    if (item.percentUsage != null) {
        this.progress = item.percentUsage!!
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

@BindingAdapter("toolbarAction2")
fun Toolbar.setToolbarAction2(isNewMessageComing: Boolean) {
    this.setIconAction2(
        ContextCompat.getDrawable(
            this.context,
            if (isNewMessageComing) R.drawable.ic_light_bulb else R.drawable.ic_light_bulb_no
        )
    )
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setSizeForDay")
fun TextView.setSizeForDay(item: BaseMultiViewHolderAdapter.BaseModelType) {
    if (item is ItemDate) {
        this.text = "(" + item.size + " Files)"
    }
}

@BindingAdapter("setImageToResoure")
fun ImageView.setImageToResoure(iv: Int) {
    this.setImageResource(iv)
}

@BindingAdapter("isCheckSelect")
fun View.isCheckSelect(listener: View.OnLongClickListener) {

}
