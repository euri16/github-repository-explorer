package dev.eury.core.utils

import kotlin.math.ln
import kotlin.math.pow

val Long.compact : String
    get() {
        if (this < 1000) return "" + this
        val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f %c", this / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }