package com.android.filemaster.utils

import android.os.Environment
import android.os.StatFs

object DeviceMemory {
    //StatFs statFs = new StatFs("/data");
    val internalStorageSpace: Long
        get() {
            val statFs = StatFs(Environment.getDataDirectory().absolutePath)
            //StatFs statFs = new StatFs("/data");
            return statFs.blockCount.toLong() * statFs.blockSize.toLong() / 1048576

        }


    //StatFs statFs = new StatFs("/data");
    val internalFreeSpace: Long
        get() {
            val statFs = StatFs(Environment.getDataDirectory().absolutePath)
            //StatFs statFs = new StatFs("/data");
            return statFs.availableBlocks.toLong() * statFs.blockSize.toLong() / 1048576
        }

    //StatFs statFs = new StatFs("/data");
    val internalUsedSpace: Long
        get() {
            val statFs = StatFs(Environment.getDataDirectory().absolutePath)
            //StatFs statFs = new StatFs("/data");
            val total =
                statFs.blockCount.toLong() * statFs.blockSize.toLong() / 1048576
            val free =
                statFs.availableBlocks.toLong() * statFs.blockSize.toLong() / 1048576
            return total - free
        }
}