package com.example.vftask.utils.resource.impl

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.example.vftask.utils.resource.ResourceWrapper

internal class ResourceWrapperImpl(
    private val context: Context
) : ResourceWrapper {

    override fun getColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(context, color)
    }

    override fun getString(id: Int): String {
        return context.getString(id)
    }


}