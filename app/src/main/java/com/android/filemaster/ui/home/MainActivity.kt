package com.android.filemaster.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.android.filemaster.R
import com.android.filemaster.databinding.ActivityMainBinding
import com.android.filemaster.utils.CheckingPermission
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            CheckingPermission.reqStoreMananger(this)
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
        initView()
        initData()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun initData() {
        binding.navigationBottom.setOnNavigationItemSelectedListener(this)
        binding.navigationBottom.itemIconTintList = null
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == CheckingPermission.REQ_FILE_MANAGER_ACCESS_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()) {
                initView()
                initData()
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
                        initView()
                        initData()
                    }
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_recents -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.homeFragment)
                return true

            }
            R.id.navigation_browser -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.browserFragment)
                return true

            }
            R.id.navigation_cleaner -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.cleanerFragment)
                return true

            }
            R.id.navigation_menu -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.menuFragment)
                return true

            }
        }
        return false
    }
}

