package com.android.filemaster.data.model

data class ListStorage(
    var imgStorage: Int,
    var nameStorage: String,
    var sizeStorage: String,
    var usedStorage: String,
    var progress: Int = 0,
    var progressMax: Int = 0,
    var tileStorage: String,
    var descrip: String

) {
}