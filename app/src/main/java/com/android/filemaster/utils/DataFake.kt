package com.android.filemaster.utils

import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.model.ListStorage

object DataFake {
    val list = listOf(
        FileCustom("Doc1", null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom("Doc2", null, null, "/storage/emulated/0/Android/data.apk"),
        FileCustom("Doc3", null, null, "/storage/emulated/0/Android/data.rar"),
        FileCustom("Doc4", null, null, "/storage/emulated/0/Android/data.docx"),
        FileCustom("Doc5", null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom("Doc6", null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom("Doc7", null, null, "/storage/emulated/0/Android/data.html"),
        FileCustom("Doc8", null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom("Doc9", null, null, "/storage/emulated/0/Android/data.zip"),
        FileCustom("Doc10", null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom("Doc11", null, null, "/storage/emulated/0/Android/data.rtf"),
        FileCustom("Doc12", null, null, "/storage/emulated/0/Android/data.pdf"),
        FileCustom("Doc13", null, null, "/storage/emulated/0/Android/data.docx"),
        FileCustom("Doc14", null, null, "/storage/emulated/0/Android/data.xlsx"),
        FileCustom("Doc15", null, null, "/storage/emulated/0/Android/data.zip"),
    )
    val listStorage = listOf(
        ListStorage("Storage", "30GB/128GB", "65% USED"),
        ListStorage("Google Storage", "30GB/128GB", "65% USED")
    )

}