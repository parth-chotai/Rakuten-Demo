package com.rakuten.demo.data.model

import com.google.gson.annotations.SerializedName


data class Photo(
    @SerializedName(KEYS.ID)
    var id: Long? = null,

    @SerializedName(KEYS.OWNER)
    var owner: String? = null,

    @SerializedName(KEYS.SECRET)
    var secret: String? = null,

    @SerializedName(KEYS.SERVER)
    var server: Long? = null,

    @SerializedName(KEYS.FARM)
    var farm: Int? = null,

    @SerializedName(KEYS.TITLE)
    var title: String? = null,

    @SerializedName(KEYS.IS_PUBLIC)
    var isPublic: Int? = null,

    @SerializedName(KEYS.IS_FRIEND)
    var isFriend: Int? = null,

    @SerializedName(KEYS.IS_FAMILY)
    var isFamily: Int? = null,
) {
    object KEYS {
        const val ID = "id"
        const val OWNER = "owner"
        const val SECRET = "secret"
        const val SERVER = "server"
        const val FARM = "farm"
        const val TITLE = "title"
        const val IS_PUBLIC = "ispublic"
        const val IS_FRIEND = "isfriend"
        const val IS_FAMILY = "isfamily"
    }
}