package com.android.filemaster.utils

import com.android.filemaster.data.model.FileDefault
import com.android.filemaster.data.model.ListStorage

object DataFake {
    val list = listOf(
        FileDefault("Doc1", "0", "0", "/storage/emulated/0/Android/data.pdf"),
        FileDefault("Doc2", "0", "0","/storage/emulated/0/Android/data.apk"),
        FileDefault("Doc3", "0", "0", "/storage/emulated/0/Android/data.rar"),
        FileDefault("Doc4", "0", "0","/storage/emulated/0/Android/data.docx"),
        FileDefault("Doc5", "0", "0","/storage/emulated/0/Android/data.pdf"),
        FileDefault("Doc6", "0", "0", "/storage/emulated/0/Android/data.pdf"),
        FileDefault("Doc7", "0", "0", "/storage/emulated/0/Android/data.html"),
        FileDefault("Doc8", "0", "0","/storage/emulated/0/Android/data.pdf"),
        FileDefault("Doc9", "0", "0","/storage/emulated/0/Android/data.zip"),
        FileDefault("Doc10", "0", "0","/storage/emulated/0/Android/data.pdf"),
        FileDefault("Doc11", "0", "0", "/storage/emulated/0/Android/data.rtf"),
        FileDefault("Doc12", "0", "0", "/storage/emulated/0/Android/data.pdf"),
        FileDefault("Doc13", "0", "0","/storage/emulated/0/Android/data.docx"),
        FileDefault("Doc14", "0", "0","/storage/emulated/0/Android/data.xlsx"),
        FileDefault("Doc15", "0", "0","/storage/emulated/0/Android/data.zip"),
    )
    val listStorage = listOf(
        ListStorage("Storage", "30GB/128GB", "65% USED"),
        ListStorage("Google Storage", "30GB/128GB", "65% USED")
    )

}