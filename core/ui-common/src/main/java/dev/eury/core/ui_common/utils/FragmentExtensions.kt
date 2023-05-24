package dev.eury.core.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.core.net.toUri
import androidx.fragment.app.Fragment

@Throws(ActivityNotFoundException::class)
fun Fragment.openUrlInBrowser(url: String ) {
    startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
}