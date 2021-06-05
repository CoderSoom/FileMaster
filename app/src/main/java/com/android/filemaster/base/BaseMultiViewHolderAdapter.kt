package com.android.filemaster.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.android.filemaster.BR

abstract class BaseMultiViewHolderAdapter<T : BaseMultiViewHolderAdapter.BaseModelType>(
    @LayoutRes private val resLayout: List<Int>
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        const val VIEW_TYPE_DEFAULT = 0
    }

    private lateinit var inflater: LayoutInflater

    var list: List<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var listener: BaseListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            resLayout[viewType],
            parent,
            false
        )
        return BaseViewHolder(binding)
    }

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

    override fun getItemCount(): Int = list?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return list?.get(position)?.viewType ?: VIEW_TYPE_DEFAULT
    }

    /**
     * Must extend this class if use multi type view holder for list
     */
    abstract class BaseModelType(open val viewType: Int = VIEW_TYPE_DEFAULT)
}