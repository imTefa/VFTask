package com.example.vftask.utils.resource

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResourceWrapper {

    fun getColor(@ColorRes color: Int): Int

    fun  getString(@StringRes id: Int): String

    //TODO implement other functions in the future
}