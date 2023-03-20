package com.rakuten.demo.extensions

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Parcelable
import com.rakuten.demo.R
import com.rakuten.demo.data.model.Photo

fun Context.getFormattedUrl(photo: Photo): String {
    val urlFormat = this.getString(R.string.url_format)
    return String.format(urlFormat, photo.server, photo.id, photo.secret)
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

fun String.getFormattedText(format: String): String {
    return String.format(format, this)
}

fun Int.getFormattedText(format: String): String {
    return String.format(format, this)
}