package com.android.filemaster.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.android.filemaster.base.adapter.callback.CallbackAdapter

abstract class BaseAdapter<T, VB : ViewDataBinding> : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T, VB>>() {
    private var dataList: MutableList<T> = arrayListOf()
    protected lateinit var binding: VB

    abstract fun getLayoutId(): Int
    abstract fun getIdVariable(): Int
    abstract fun getIdVariableOnClicked(): Int?

    @Nullable
    abstract fun getOnClicked(): CallbackAdapter?

    class BaseViewHolder<T, VB : ViewDataBinding>(var binding: VB) : RecyclerView.ViewHolder(binding.root) {
        fun setVariable(id: Int, t: T) {
            this.binding.setVariable(id, t)
        }

        fun setClickItemAdapter(id: Int, callback: CallbackAdapter) {
            this.binding.setVariable(id, callback)
        }
    }

    override fun getItemCount() = this.dataList.size

    override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
        holder.setVariable(getIdVariable(), this.dataList[position])
        if (getOnClicked() != null) {
            holder.setClickItemAdapter(this.getIdVariableOnClicked()!!, getOnClicked()!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, VB> {
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayoutId(), parent, false)
        return BaseViewHolder(binding)
    }
}