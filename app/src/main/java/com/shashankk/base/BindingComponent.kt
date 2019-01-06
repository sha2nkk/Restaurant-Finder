package com.shashankk.base

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import java.lang.Exception

object BindingComponent {

    @JvmStatic
    @BindingAdapter("OnClick")
    fun setClickListner(v: View, function: (() -> Unit)?) {
        v.setOnClickListener {
            function?.invoke()
        }
    }

    @JvmStatic
    @BindingAdapter("bgColor")
    fun bindViewBackgroundColor(v: View, colorCode: String?) {
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

    @JvmStatic
    @BindingAdapter("app:image")
    fun bindImage(iv: ImageView, url: String?) {
        url?.let {
            Glide.with(iv).load(url).into(iv)
        }

    }

    @JvmStatic
    @BindingAdapter("app:title")
    fun bindCollapsingToolbarLayoutTitle(v: CollapsingToolbarLayout, title: String?) {
        v.title = title
        v.setExpandedTitleColor(Color.parseColor("#FFFFFF"))
        v.setCollapsedTitleTextColor(Color.parseColor("#FFFFFF"))
    }

    @JvmStatic
    @BindingAdapter("app:selected")
    fun setSelected(v: TextView, isSelected: Boolean?) {
        v.isSelected = isSelected == true
    }

}