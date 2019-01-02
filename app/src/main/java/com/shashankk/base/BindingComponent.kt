package com.shashankk.base

import android.graphics.Color
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shashankk.restaurant_finder.R
import java.lang.Exception

object BindingComponent {

    @JvmStatic
    @BindingAdapter("OnClick")
    fun setClickListner(v: View, function: () -> Unit) {
        v.setOnClickListener {
            function()
        }
    }

    @JvmStatic
    @BindingAdapter("bgColor")
    fun bindViewBackgroundColor(v: View, colorCode: String) {
        try {
            v.setBackgroundColor(Color.parseColor(colorCode))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun bindVisibility(v: View, isVisible: Boolean?) {
        try {
            v.visibility = if (isVisible == true) {
                View.VISIBLE
            } else {
                View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}