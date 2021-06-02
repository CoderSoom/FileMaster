package com.android.filemaster.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.android.filemaster.R
import com.android.filemaster.base.BaseActivity
import com.android.filemaster.databinding.ActivityHomeBinding
import com.android.filemaster.utils.CheckingPermission
import com.android.filemaster.utils.FileManager
import java.io.File


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    private val TAG: String? = "HomeActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId() = R.layout.activity_home

    override fun getViewModel() = HomeViewModel::class.java


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun init() {

        showImages()
        showAudio()
        showRecent()
//        copyFile()
//        moveFile()
//        renameFileA()
        moveFileSDCard()
    }

    private fun renameFileA() {
        FileManager.renameFile(this, "/storage/emulated/0/Download/docD.txt", "docE.txt")
    }
    fun moveFileSDCard(){
        var a = Environment.getExternalStorageDirectory()
        Log.d(TAG, "moveFileSDCard: "+a.toString())
        var file = File("/storage/emulated/0/Music/Anh Thơ - Đừng Ví Em Là Biển.mp3")
        var fileMove = File("/storage/emulated/0/Download/")
        FileManager.moveFileSDCard(this, file, fileMove.toString())
    }

    private fun moveFile() {
        var linkFile = "/storage/emulated/0/Music/Lac Troi - Son Tung M-TP_ [MP3 128kbps] (1).mp3"
        var copyFile = "/storage/emulated/0/Music/"
        var file = File(linkFile)
        var dst = File(copyFile)
        FileManager.moveFile(file, dst)
    }

    private fun copyFile() {
        lateinit var dst: File
        lateinit var a: File
        var linkFile = "/storage/emulated/0/Download/Lac Troi - Son Tung M-TP_ [MP3 128kbps] (1).mp3"
        var copyFile = "/storage/emulated/0/Music/"

        dst = File(copyFile)
        a = File(linkFile)
        FileManager.copy(a, dst)

    }


    fun showRecent() {
        FileManager.getListRecent(this, 10)
    }

    private fun showAudio() {
        Log.d(TAG, FileManager.getListAudio(this).size.toString())
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    fun showImages() {
        if (CheckingPermission.checkPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
        ) {
//            var a = FileManager.getListImage(this@HomeActivity)

//        Log.d(TAG, "init: " + a.size)
//            for (ab in a) {
//                Log.d(TAG, "init: " + ab)
//                Toast.makeText(this@HomeActivity, ab.nameImages, Toast.LENGTH_SHORT).show()
//                Toast.makeText(this@HomeActivity, ab.dataImages, Toast.LENGTH_SHORT).show()
//                Toast.makeText(this@HomeActivity, ab.sizeImages, Toast.LENGTH_SHORT).show()
//                Toast.makeText(
//                    this@HomeActivity,
//                    "date" + ab.dateImages.toString(),
//                    Toast.LENGTH_SHORT
//                )
//                    .show()
//            }
//            var b = FileManager.getListAudio(this@HomeActivity)
//            for (ab in b) {
//                Log.d(TAG, "init: " + ab)
//                Toast.makeText(this@HomeActivity, ab.nameImages, Toast.LENGTH_SHORT).show()
//                Toast.makeText(this@HomeActivity, ab.dataImages, Toast.LENGTH_SHORT).show()
//                Toast.makeText(this@HomeActivity, ab.sizeImages, Toast.LENGTH_SHORT).show()
//                Toast.makeText(
//                    this@HomeActivity,
//                    "date" + ab.dateImages.toString(),
//                    Toast.LENGTH_SHORT
//                )
//                    .show()
//            }

        }


    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}