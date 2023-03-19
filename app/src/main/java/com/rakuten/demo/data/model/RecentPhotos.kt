package com.rakuten.demo.data.model

import com.google.gson.annotations.SerializedName


data class RecentPhotos(
    @SerializedName(KEYS.PHOTOS)
    var photos: Photos? = Photos(),

    @SerializedName(KEYS.STAT)
    var stat: String? = null,
) {
    object KEYS {
        const val PHOTOS = "photos"
        const val STAT = "stat"
    }
}

