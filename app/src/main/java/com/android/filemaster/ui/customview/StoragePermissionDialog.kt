package com.android.filemaster.ui.customview

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.android.filemaster.R
import com.android.filemaster.base.BaseDialog
import com.android.filemaster.databinding.StoragePermissionDialogBinding

class StoragePermissionDialog(context: Context) :
    BaseDialog(context), View.OnClickListener {
    private var isCancel: Boolean = false
    private var listener: OnActionListener? = null
    private var mBinding: StoragePermissionDialogBinding? = null

    fun setTitle(textId: Int): StoragePermissionDialog {
        this.mBinding!!.tvTitle.setText(textId)
        return this
    }

    fun setTitle(title: CharSequence?): StoragePermissionDialog {
        this.mBinding!!.tvTitle.text = title
        return this
    }

    fun setButtonAllowText(textId: Int): StoragePermissionDialog {
        this.mBinding!!.tvPositiveButton.setText(textId)
        return this
    }

    fun setButtonCancelText(textId: Int): StoragePermissionDialog {
        this.mBinding!!.tvNegativeButton.setText(textId)
        return this
    }

    fun setListener(listener: OnActionListener): StoragePermissionDialog {
        this.listener = listener
        return this
    }

    fun setMessage(textId: Int): StoragePermissionDialog {
        this.mBinding!!.tvMessage.setText(textId)
        return this
    }

    fun setMessage(message: CharSequence): StoragePermissionDialog {
        this.mBinding!!.tvMessage.text = message
        return this
    }

    fun setCancelable(cancel: Boolean): StoragePermissionDialog {
        this.isCancel = cancel
        return this
    }

    override fun isCancelable(): Boolean {
        return this.isCancel
    }

    override fun getDialogView(): View? {
        try {
            this.mBinding = StoragePermissionDialogBinding.inflate(LayoutInflater.from(context))
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
        if (TextUtils.isEmpty(mBinding!!.tvMessage.text)) {
            mBinding!!.tvMessage.visibility = View.GONE
        }
    }
}