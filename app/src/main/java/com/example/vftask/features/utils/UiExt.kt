package com.example.vftask.features.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.example.vftask.R
import com.squareup.picasso.Picasso

fun ImageView.loadImageFromUrl(url: String) {
    Picasso.get().load(url).placeholder(R.drawable.ic_loading).fit().into(this)
}

fun ImageView.loadImageFromDrawable(@DrawableRes drawable: Int) {
    setImageDrawable(ContextCompat.getDrawable(context, drawable))
}