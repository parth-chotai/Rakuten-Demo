package com.rakuten.demo.data.api

import com.rakuten.demo.data.model.PhotosMetadata
import com.rakuten.demo.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface IRakutenApiService {

    @GET(Constants.PHOTOS_JSON)
    suspend fun getRecentPhotos(
        @QueryMap queries: Map<String, String>,
    ): Response<PhotosMetadata>
}