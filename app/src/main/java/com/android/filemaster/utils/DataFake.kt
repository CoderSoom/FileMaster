package com.android.filemaster.utils

import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.model.ListStorage

object DataFake {
    val list = listOf(
        FileCustom(null, null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom(null, null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom(null, null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom(null, null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom(null, null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom(null, null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom(null, null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom(null, null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom(null, null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom(null, null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom(null, null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom(null, null, null, "/storage/emulated/0/Android/data.pdf"),
    )
    val listStorage = listOf(
        ListStorage("Storage", "30GB/128GB", "65% USED"),
        ListStorage("Google Storage", "30GB/128GB", "65% USED")
    )

}