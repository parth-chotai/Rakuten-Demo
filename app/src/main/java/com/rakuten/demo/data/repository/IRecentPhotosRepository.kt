package com.rakuten.demo.data.repository

import com.rakuten.demo.data.model.PhotosMetadata
import com.rakuten.demo.util.NetworkResult

interface IRecentPhotosRepository {

    suspend fun getRecentPhotos(): NetworkResult<PhotosMetadata>
}