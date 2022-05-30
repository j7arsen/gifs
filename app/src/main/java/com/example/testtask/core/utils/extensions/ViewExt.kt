package com.example.testtask.core.utils.extensions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.testtask.R

fun ImageView.loadGif(gif: String) = Glide.with(context)
    .asGif()
    .placeholder(R.drawable.placeholder)
    .load(gif)
    .override(140, 140)
    .error(R.drawable.ic_error)
    .transition(DrawableTransitionOptions.withCrossFade())
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .centerCrop()
    .into(this)

fun ImageView.loadGifFullScreen(gif: String) = Glide.with(context)
    .asGif()
    .placeholder(R.drawable.placeholder)
    .load(gif)
    .error(R.drawable.ic_error)
    .transition(DrawableTransitionOptions.withCrossFade())
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .centerCrop()
    .into(this)

fun View.hide() {
    isVisible = false
}

fun View.show() {
    isVisible = true
}

fun TextView.showWithText(textToShow: String) {
    show()
    text = textToShow
}
