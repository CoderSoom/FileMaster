package com.android.filemaster.ui.menu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.BaseFragment
import com.android.filemaster.base.BaseMultiViewHolderAdapter
import com.android.filemaster.data.model.ItemAction
import com.android.filemaster.data.model.ItemDate
import com.android.filemaster.data.model.ItemLanguage
import com.android.filemaster.data.model.ItemMore
import com.android.filemaster.databinding.FragmentMenuBinding


class MenuFragment : BaseFragment<FragmentMenuBinding>() {
    private var languageAdapter = LanguageAdapter()
    private var actionMoreByInnosoftAdapter = ActionMoreByInnosoftAdapter()
    private var actionShareAdapter = ActionShareAdapter()
    private var actionSettingAdapter = ActionSettingAdapter()
    override fun getLayoutId(): Int {
        return R.layout.fragment_menu
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setUpRecylerView()

    }

    private fun setUpRecylerView() {
        binding.rcShareFile.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = actionShareAdapter
        }

        binding.rcMoreFile.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = actionMoreByInnosoftAdapter
        }

        binding.rcSetting.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = actionSettingAdapter
        }



    }


    private fun initData() {

        val listShare = mutableListOf<ItemAction>()
        listShare.add(
            ItemAction(
                getString(R.string.share_file_to_mobile),
                R.drawable.ic_share_file_mobile
            )
        )
        listShare.add(ItemAction(getString(R.string.file_transfer_pc), R.drawable.file_transfer_pc))
        listShare.add(ItemAction(getString(R.string.app_manager), R.drawable.ic_app_manager))

        val listMore = mutableListOf<ItemMore>()
        listMore.add(
            ItemMore(
                getString(R.string.this_is_paragraph),
                getString(R.string.censored),
                R.drawable.ic_azip
            )
        )
        listMore.add(
            ItemMore(
                getString(R.string.this_is_paragraph),
                getString(R.string.censored),
                R.drawable.ic_aclear
            )
        )
        listMore.add(
            ItemMore(
                getString(R.string.this_is_paragraph),
                getString(R.string.censored),
                R.drawable.ic_ascan
            )
        )

        val listSetting = mutableListOf<ItemAction>()
        listSetting.add(ItemAction(getString(R.string.setting), R.drawable.ic_setting))
        listSetting.add(ItemAction(getString(R.string.rate_us), R.drawable.ic_star))
        listSetting.add(ItemAction(getString(R.string.feed_back), R.drawable.ic_speech))
        listSetting.add(ItemAction(getString(R.string.about), R.drawable.ic_info))

        val listLanguage = mutableListOf<ItemLanguage>()
        listLanguage.add(ItemLanguage(getString(R.string.en)))
        listLanguage.add(ItemLanguage(getString(R.string.vi)))
        listLanguage.add(ItemLanguage(getString(R.string.indo)))

        val moreApp = mutableListOf<BaseMultiViewHolderAdapter.BaseModelType>()
        moreApp.addAll(listMore)
        moreApp.add(0, ItemDate(getString(R.string.more_by_insoft), null))

        actionShareAdapter.list = listShare
        actionSettingAdapter.list = listSetting
        actionMoreByInnosoftAdapter.list = moreApp

        actionShareAdapter.listener = object : ActionShareAdapter.IShare {
            override fun onItemClick(position: Int, itemAction: ItemAction) {
                ChooseLanguageDialog(requireActivity())
                    .setAdapter(languageAdapter, requireActivity(), listLanguage)
                    .setButtonCancelText(R.string.cancel_permission)
                    .setButtonAllowText(R.string.ok)
                    .setCancelable(false)
                    .setListener(object : ChooseLanguageDialog.OnActionListener {
                        override fun onAccept() {
                        }

                        override fun onCancel() {

                        }
                    }).show()
            }
        }

    }


}