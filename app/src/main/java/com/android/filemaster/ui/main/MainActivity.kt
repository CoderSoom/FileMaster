package com.android.filemaster.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.android.filemaster.R
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.ActivityMainBinding
import com.android.filemaster.ui.customview.StoragePermissionDialog
import com.android.filemaster.utils.AppPrefs
import com.android.filemaster.utils.CheckingPermission
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var isStartSettingPermission = false
    private val mainViewModel by viewModels<MainViewModel>()
    protected var mBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val permisionDialog = StoragePermissionDialog(this)
                permisionDialog.setTitle(R.string.grant_permission)
                    .setMessage(
                        getString(
                            R.string.permission_message_android_11,
                            getString(R.string.app_name)
                        )
                    ).setButtonAllowText(R.string.allow_permission)
                    .setButtonCancelText(R.string.cancel_permission)
                    .setListener(object : StoragePermissionDialog.OnActionListener {
                        override fun onAccept() {
                            CheckingPermission.requestManageStoragePermission(this@MainActivity)
                        }

                        override fun onCancel() {
                            finish()
                        }
                    }).show()
                return
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CheckingPermission.isNotStoragePmsGranted(this)) {
                ActivityCompat.requestPermissions(
                    this,
                    CheckingPermission.PERMISSION_STORAGE,
                    CheckingPermission.REQ_STORAGE_PERMISSION_CODE
                )
                return
            }
        }
        this.initData()
    }

    private fun initData() {
        println("DUYDQ->  [initData]")
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mBinding!!.lifecycleOwner = this
        mBinding!!.navigationBottom.setOnNavigationItemSelectedListener(this)
        mBinding!!.navigationBottom.itemIconTintList = null
        mBinding!!.mainViewModel = mainViewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == CheckingPermission.REQUEST_CODE_MANAGE_STORAGE_PERMISSION) {
            requestPermission()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CheckingPermission.REQ_STORAGE_PERMISSION_CODE) {
            val instance = AppPrefs.instance
            for (permission in permissions) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            permission
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                            this.initData()
                            return
                        }
                    } else if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                        instance!!.applyDontShowAgain()
                    }
                }
            }
            if (instance!!.isDontShowAgain) {
                showDialogPermission()
            } else {
                finish()
            }
        }
    }

    private fun showDialogPermission() {
        StoragePermissionDialog(this)
            .setTitle(R.string.grant_permission)
            .setMessage(R.string.description_read_external_storage)
            .setButtonCancelText(R.string.cancel_permission)
            .setButtonAllowText(R.string.go_setting)
            .setCancelable(false)
            .setListener(object : StoragePermissionDialog.OnActionListener {
                override fun onAccept() {
                    CheckingPermission.goSettingsForPermission(this@MainActivity)
                    isStartSettingPermission = true
                }

                override fun onCancel() {
                    finish()
                }
            }).show()
    }

    override fun onResume() {
        super.onResume()
        if (isStartSettingPermission) {
            if (CheckingPermission.isNotStoragePmsGranted(this)) {
                finish()
                return
            }
            this.initData()
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


