package com.android.filemaster.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
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
    private var mBinding: ActivityMainBinding? = null
    private var doubleBackToExitPressedOnce: Boolean = false
    private var currentFragmentId: Int? = null

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
        if (item.itemId == currentFragmentId) {
            return false
        }
        currentFragmentId = item.itemId
        when (item.itemId) {
            R.id.navigation_recents -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.homeFragment)
                return true
            }
            R.id.navigation_browser -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.filesFragment)
                return true
            }
            R.id.navigation_cleaner -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.cleaner_fragment)
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

    override fun onBackPressed() {
        try {
            var handled = false
            supportFragmentManager.fragments.forEach { fragment ->
                if (fragment is NavHostFragment) {
                    fragment.childFragmentManager.fragments.forEach { childFragment ->
                        if (childFragment is BaseFragment<*>) {
                            handled = childFragment.onBackPressed()
                            if (handled)
                                return
                        }
                    }
                }
            }
            if (!handled) {
                doubleBackToExit()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            doubleBackToExit()
        }
    }

    private fun doubleBackToExit() {
        if (this.doubleBackToExitPressedOnce) {
            finish()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.back_to_exit_app), Toast.LENGTH_SHORT)
            .show()
        Handler(Looper.getMainLooper()!!).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}


