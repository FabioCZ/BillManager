package com.gottlicher.billmanager.utils

import android.graphics.drawable.Drawable
import android.databinding.BindingAdapter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.widget.ImageView


@BindingAdapter("app:bindSrc")
fun bindSrc(imageView: ImageView, drawable: Drawable?) {
    if(drawable == null) return
    imageView.setImageDrawable(drawable)
}

@BindingAdapter("app:bindSrcFade")
fun bindSrcFade(imageView: ImageView, drawable: Drawable?) {
    if(drawable == null) return
    val td = TransitionDrawable(arrayOf(ColorDrawable(Color.TRANSPARENT), drawable))
    imageView.setImageDrawable(td)
    td.startTransition(400)
}
