package com.rakuten.demo.data.model

import com.google.gson.annotations.SerializedName


data class RecentPhotos(
    @SerializedName(KEYS.PAGE)
    var page: Int? = null,

    @SerializedName(KEYS.PAGES)
    var pages: Int? = null,

    @SerializedName(KEYS.PER_PAGE)
    var perpage: Int? = null,

    @SerializedName(KEYS.TOTAL)
    var total: Int? = null,

    @SerializedName(KEYS.PHOTO)
    var photos: List<Photo> = listOf(),
) {
    object KEYS {
        const val PAGE = "page"
        const val PAGES = "pages"
        const val PER_PAGE = "perpage"
        const val TOTAL = "total"
        const val PHOTO = "photo"
    }
}

