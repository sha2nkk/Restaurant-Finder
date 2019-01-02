package com.shashankk.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private var viewModels: List<ViewModel>,
                          private val viewProvider: ViewProvider) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.setVariable(BR.vm, viewModels[position])
        holder.binding.setLifecycleOwner(viewProvider.getLifecycleOwner())
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = viewModels.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val holder = ViewHolder(DataBindingUtil.inflate(inflater, viewType, parent, false))
        return holder
    }

    override fun getItemViewType(position: Int) = viewProvider.getLayout(viewModels[position]::class.java)

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateData(newVM: List<ViewModel>) {
        viewModels = newVM
        notifyDataSetChanged()
    }
}
