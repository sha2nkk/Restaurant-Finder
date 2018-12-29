package com.shashankk.base

import android.view.View
import androidx.databinding.BindingAdapter

object BindingComponent {

    @JvmStatic
    @BindingAdapter("OnClick")
    fun setClickListner(v:View, function: ()->Unit) {
        v.setOnClickListener {
            function()
        }
    }
}