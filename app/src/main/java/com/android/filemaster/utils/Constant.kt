package com.android.filemaster.utils

object Constant {

    //Type file to get icon
    const val TF_JPG = "jpg"
    const val TF_PNG = "png"
    const val TF_MP3 = "mp3"
    val TF_MP4 = arrayListOf(
        "mp4",
        "mov",
        "mpeg4",
        "webm",
        "mkv",
        "flv",
        "vob",
        "ogg",
        "avi",
        "wmv",
        "viv",
        "mpeg",
        "m4v",
        "3gp"
    )
    val TF_HTML =
        arrayListOf(
            "html",
            "html",
            "shtml",
            "shtm",
            "xhtml",
            "xht",
            "hta",
            "xml",
            "xaml",
            "xsl",
            "xslt",
            "xsd", "xul", "kml", "svg", "mxml", "xsml"
        )
    const val TF_ZIP = "zip"
    const val TF_PDF = "pdf"
    const val TF_APK = "apk"
    const val TF_RAR = "rar"
    const val TF_XLS = "xls"
    const val TF_XLSX = "xlsx"
    const val TF_PPT = "ptt"
    const val TF_PPTX = "pptx"
    val TF_DOC = arrayListOf(
        "txt",
        "doc",
        "docx",
        "rtf",
        "rfx",
        "ltx",
        "docm",
        "org",
        "dsc",
        "text",
        "wps",
        "log",
        "md5.txt",
        ""
    )
    const val IC_MORE = "more"
    const val IC_PLACE_HOLDER = "xxx"

    //Viewtype for multiviewholder
    const val VIEW_TYPE_DATE = 0
    const val VIEW_TYPE_ITEM = 1
    const val VIEW_TYPE_NULL = 2
}