package com.rakuten.demo.data.model

import com.google.gson.annotations.SerializedName


data class PhotosMetadata(
    @SerializedName(KEYS.PHOTOS)
    var recentPhotos: RecentPhotos? = RecentPhotos(),

    @SerializedName(KEYS.STAT)
    var stat: String? = null,
) {
    object KEYS {
        const val PHOTOS = "photos"
        const val STAT = "stat"
    }
}

