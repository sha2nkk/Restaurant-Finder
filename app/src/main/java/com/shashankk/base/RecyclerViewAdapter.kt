package com.shashankk.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.shashankk.model.Venue
import com.shashankk.restaurant_finder.BR
import com.shashankk.restaurant_finder.R

class RecyclerViewAdapter(
    private var viewModels: List<ViewModel>,
    private val viewProvider: ViewProvider
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var isLoaderVisible: Boolean = false

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (isLoaderVisible && position == viewModels.size) {
            return
        }
        holder.binding.setVariable(BR.vm, viewModels[position])
        holder.binding.setLifecycleOwner(viewProvider.getLifecycleOwner())
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = viewModels.size + if (isLoaderVisible) 1 else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val holder = ViewHolder(DataBindingUtil.inflate(inflater, viewType, parent, false))
        return holder
    }

    override fun getItemViewType(position: Int) = when {
        (position < viewModels.size) -> viewProvider.getLayout(viewModels[position]::class.java)
        else -> R.layout.item_loader

    }

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateData(newVM: List<ViewModel>) {
        viewModels = newVM
        notifyDataSetChanged()
    }

    fun setLoaderVisibilty(visibility: Boolean) {
        isLoaderVisible = visibility
        notifyDataSetChanged()
    }


    private fun RecyclerViewAdapter.removeItem(venue: Venue) {

    }
}
