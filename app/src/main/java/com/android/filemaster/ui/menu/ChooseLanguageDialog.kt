package com.android.filemaster.ui.menu

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.filemaster.R
import com.android.filemaster.base.BaseDialog
import com.android.filemaster.data.model.ItemLanguage
import com.android.filemaster.databinding.ChooseLanguageDialogBinding

class ChooseLanguageDialog(context: Context) : BaseDialog(context), View.OnClickListener {
    private var listener: OnActionListener? = null
    private var mBinding: ChooseLanguageDialogBinding? = null

    fun setButtonAllowText(textId: Int): ChooseLanguageDialog {
        this.mBinding!!.tvPositiveButton.setText(textId)
        return this
    }

    fun setButtonCancelText(textId: Int): ChooseLanguageDialog {
        this.mBinding!!.tvNegativeButton.setText(textId)
        return this
    }

    fun setListener(listener: OnActionListener): ChooseLanguageDialog {
        this.listener = listener
        return this
    }

    fun setAdapter(
        adapterLanguage: LanguageAdapter,
        context: Context,
        listLanguage: MutableList<ItemLanguage>
    ): ChooseLanguageDialog {
        this.mBinding!!.rcLanguage.apply {
            adapter = adapterLanguage
            layoutManager = LinearLayoutManager(context)

        }
        adapterLanguage.list = listLanguage
        return this
    }

    fun setCancelable(cancel: Boolean): ChooseLanguageDialog {
        this.isCancelable = cancel
        return this
    }

    override var isCancelable = false
        private set

    override fun getDialogView(): View? {
        try {
            this.mBinding = ChooseLanguageDialogBinding.inflate(LayoutInflater.from(context))
            this.mBinding!!.tvNegativeButton.setOnClickListener(this)
            this.mBinding!!.tvPositiveButton.setOnClickListener(this)
            return this.mBinding!!.root
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_positive_button -> if (this.listener != null) {
                dismiss()
                this.listener!!.onAccept()
            }
            R.id.tv_negative_button -> if (this.listener != null) {
                dismiss()
                this.listener!!.onCancel()
            }
        }
    }

    interface OnActionListener {
        fun onCancel()
        fun onAccept()
    }

    override fun onDismissDialog() {
    }

    override fun onShowingDialog() {
        if (TextUtils.isEmpty(mBinding!!.tvNegativeButton.text)) {
            mBinding!!.tvNegativeButton.visibility = View.GONE
        }
//        if (TextUtils.isEmpty(mBinding!!.tvMessage.text)) {
//            mBinding!!.tvMessage.visibility = View.GONE
//        }
    }
}