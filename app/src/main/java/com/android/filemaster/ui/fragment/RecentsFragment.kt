package com.android.filemaster.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.data.adapter.RecentApdapter
import com.android.filemaster.data.model.FileCustom
import com.android.filemaster.data.viewmodel.FileViewModel
import com.android.filemaster.databinding.FragmentRecentsBinding
import com.android.filemaster.model.FileDefault
import com.android.filemaster.model.ItemFileRecent
import com.android.filemaster.module.getApplication
import com.android.filemaster.ui.main.MainActivity
import com.android.filemaster.viewmodel.RecentsViewModel
import java.util.*

class RecentsFragment : BaseFragment<FragmentRecentsBinding>() {
    val TAG = "xx"

    private val viewModel by viewModels<RecentsViewModel>()
    private val recentAdapter = RecentApdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_recents
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcListRecents.adapter = recentAdapter
        binding.rcListRecents.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener{
            Log.i(TAG, "onViewCreated: ")
                requireActivity().onBackPressed()
        }
        viewModel.data.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            val todayFileDefault = mutableListOf<FileCustom>()
            val weekFileDefault = mutableListOf<FileCustom>()

            val today = mutableListOf<ItemFileRecent>()
            Log.d(TAG, "setBindingViewModel: " + cal.timeInMillis / 1000)

            for (recent in it) {
                if (recent.date!!.toLong() > cal.timeInMillis / 1000) {
                    todayFileDefault.add(recent)
                } else {
                    weekFileDefault.add(recent)

                }
            }
            today.add(
                ItemFileRecent(
                    "Today",
                    "(" + todayFileDefault.size.toString() + " File)",
                    todayFileDefault
                )
            )
            today.add(
                ItemFileRecent(
                    "The Week",
                    "(" + weekFileDefault.size.toString() + " File)",
                    weekFileDefault
                )
            )

            recentAdapter.list = today
            recentAdapter.notifyDataSetChanged()
        })
    }

//    override fun onClick(v: View?) {
//        when(v!!.id){
//            R.id.toolbar ->{
//                print("sdaffdsafdasfdasf")
//
//                (activity as MainActivity).onBackPressed()}
//        }
//    }
}