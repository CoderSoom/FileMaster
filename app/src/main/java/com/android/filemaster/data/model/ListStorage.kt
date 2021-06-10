package com.android.filemaster.data.model

data class ListStorage(
    var imgStorage: Int,
    var nameStorage: String,
    var totalMemorySize: Long?,
    var amountOfMemoryUsed: Long?,
    var percentUsage: Float?,
    var usedStorage: String?,
    var sizeName: String?
) {
}