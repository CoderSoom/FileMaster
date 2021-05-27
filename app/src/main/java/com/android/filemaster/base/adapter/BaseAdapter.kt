package com.android.filemaster.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.android.filemaster.BR

abstract class BaseAdapter<T : Any>(
    @LayoutRes private val resLayout: Int
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {

    var listener: ListItemListener? = null
    var list: List<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, resLayout, parent, false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding.apply {
            setVariable(BR.item, list?.get(position))
            setVariable(BR.itemPosition, position)
            setVariable(BR.itemListener, listener)
            val context = root.context as LifecycleOwner
            lifecycleOwner = context
            executePendingBindings()
        }
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.clearAnimation()
    }

    class BaseViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun clearAnimation() {
            binding.root.clearAnimation()
        }
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.clearAnimation()
    }

    /**
     * All listener of adapter must implement this interface
     */
    interface ListItemListener
}