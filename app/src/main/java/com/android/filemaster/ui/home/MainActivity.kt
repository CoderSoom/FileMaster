package com.android.filemaster.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.adapter.RecentsAdaper
import com.android.filemaster.databinding.ActivityHomeBinding
import com.android.filemaster.ui.fragment.BrowserFragment
import com.android.filemaster.ui.fragment.CleanerFragment
import com.android.filemaster.ui.fragment.MenuFragment
import com.android.filemaster.ui.fragment.RecentsFragment
import com.android.filemaster.utils.CheckingPermission
import com.android.filemaster.utils.FileManager
import com.android.filemaster.viewmodel.RecentsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var model: RecentsViewModel
    private val TAG = "h.MainActivity"
    var adaper = RecentsAdaper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            CheckingPermission.reqStoreMananger(this)
            Log.i(TAG, "onCreate: vao day")

            return

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CheckingPermission.isNotStoragePmsGranted(this)) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    CheckingPermission.REQ_STORAGE_PERMISSION_CODE
                )
                return
            }
        }

        initData()
        initView()


    }

    private fun initData() {
        model = RecentsViewModel()
        model.getData(this)
        binding.navigation.setOnNavigationItemSelectedListener(this)
        binding.includeRecents.rcListRecents.adapter = adaper
        binding.includeRecents.rcListRecents.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.includeRecents.rcListRecents.setNestedScrollingEnabled(true)
        binding.navigation.itemIconTintList = null

    }

    private fun initView() {
        Log.i(
            TAG,
            "initView: " + FileManager.setImageFile("/storage/B8B0-9BB9/Android/media/com.mp3")
        )
        model.data.observe(this, Observer {
            if (it.size <= 4) {
                adaper.list = it
                binding.includeRecents.clMore.visibility = View.INVISIBLE
            } else {
                adaper.list = it.subList(0, 3)
                binding.includeRecents.clMore.visibility = View.VISIBLE
                binding.includeRecents.clMore.setOnClickListener {
                    val tran = supportFragmentManager.beginTransaction()
                    tran.replace(R.id.fl, RecentsFragment())
                    tran.commit()
                }
            }
            for (i in 0..it.size - 1) {
                Log.d(TAG, "initView: " + it[i].path)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == CheckingPermission.REQ_FILE_MANAGER_ACCESS_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()) {
                initView()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CheckingPermission.REQ_STORAGE_PERMISSION_CODE) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    if (permission.equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        initData()
                        initView()
                    }
                }
            }
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.navigation_recents -> {
                val tran = supportFragmentManager.beginTransaction()
                tran.replace(R.id.fl, RecentsFragment())
                tran.commit()
                return true

            }
            R.id.navigation_browser -> {

                val tran = supportFragmentManager.beginTransaction()
                tran.replace(R.id.fl, BrowserFragment())
                tran.commit()
                return true

            }
            R.id.navigation_cleaner -> {
                val tran = supportFragmentManager.beginTransaction()
                tran.replace(R.id.fl, CleanerFragment())
                tran.commit()
                return true

            }
            R.id.navigation_menu -> {
                val tran = supportFragmentManager.beginTransaction()
                tran.replace(R.id.fl, MenuFragment())
                tran.commit()
                return true

            }
        }
        return false
    }
}