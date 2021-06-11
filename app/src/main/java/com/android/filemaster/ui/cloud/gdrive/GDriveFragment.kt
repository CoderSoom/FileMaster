package com.android.filemaster.ui.cloud.gdrive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.filemaster.MyApp
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.viewmodel.MainViewModel
import com.android.filemaster.databinding.FragmentGdriveBinding
import com.android.filemaster.ui.home.ToolbarActionListener
import com.android.filemaster.utils.AppPrefs
import com.tapon.ds.view.toolbar.Toolbar

class GDriveFragment : BaseFragment<FragmentGdriveBinding>(), ToolbarActionListener {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val gDriveViewModel by viewModels<GDriveViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_gdrive
    }

    override fun getToolbar(): Toolbar {
        return this.binding.toolbarCloud
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initData()
    }

    private fun initData() {
        this.binding.toolbarCloud.setOnToolbarActionListener(this)

        if (!gDriveViewModel.isIntegratedCloud) {
            this.binding.toolbarCloud.toolbar()
                .findViewById<ViewGroup>(R.id.container_dropdown_type).visibility = View.GONE
            this.binding.toolbarCloud.setTitle(activityOwner.getString(R.string.cloud))
        } else {
            this.binding.toolbarCloud.setIconAction1(
                MyApp.resource().getDrawable(R.drawable.ic_search_recent)
            )
            this.binding.toolbarCloud.setIconAction2(
                MyApp.resource().getDrawable(R.drawable.ic_toolbar_more_action)
            )
        }
        this.binding.btnAddDrive.setOnClickListener {

        }
    }

    override fun onBackPressed(): Boolean {
        this.backToHome()
        return true
    }

    private fun backToHome() {
        this.mainViewModel.showMenu()
        findNavController().popBackStack(R.id.homeFragment, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> backToHome()
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }

    override fun onAction1Click() {
    }

    override fun onAction2Click() {
    }

    override fun onDropdownListener(textFilter: CharSequence) {

    }
}