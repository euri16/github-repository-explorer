package dev.eury.core.ui_common.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

fun ImageView.load(url: String, cornerRadius: Int? = null, drawablePlaceHolder: Drawable? = null) {
    val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
    Glide.with(context)
        .load(url)
        .transition(withCrossFade(factory))
        .let { builder ->
            drawablePlaceHolder?.let {
                builder.placeholder(it)
            }
            cornerRadius?.let {
                builder.apply(RequestOptions().transform(RoundedCorners(cornerRadius)))
            } ?: builder
        }.into(this)
}