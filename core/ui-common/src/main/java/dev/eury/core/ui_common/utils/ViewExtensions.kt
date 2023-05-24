package dev.eury.core.ui_common.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> ViewGroup.inflateViewBinding(
    inflateFunc: (LayoutInflater, ViewGroup, Boolean) -> T
) = inflateFunc(LayoutInflater.from(context), this, false)

private fun Context.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable? {
    return ResourcesCompat.getDrawable(resources, drawableRes, null)
}

fun Fragment.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable? {
    return requireContext().getDrawableCompat(drawableRes)
}

fun DividerItemDecoration.setDrawableCompat(context: Context, @DrawableRes drawableRes: Int) {
    context.getDrawableCompat(drawableRes)?.let { setDrawable(it) }
}

fun RecyclerView.addVerticalItemDecoration(@DrawableRes drawableRes: Int) {
    val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    itemDecoration.setDrawableCompat(context, drawableRes)
    addItemDecoration(itemDecoration)
}
