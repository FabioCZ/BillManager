package com.gottlicher.billmanager.utils

import android.graphics.drawable.Drawable
import android.databinding.BindingAdapter
import android.widget.ImageView


@BindingAdapter("app:bindSrc")
fun bindSrcCompat(imageView: ImageView, drawable: Drawable) {
    imageView.setImageDrawable(drawable)
}
