package com.shashankk.base

import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

interface ViewProvider {

    @LayoutRes
    fun getLayout(model: Class<out ViewModel>): Int

    fun getLifecycleOwner(): LifecycleOwner? {
        return null
    }
}
