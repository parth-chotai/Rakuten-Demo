package com.rakuten.demo.data.repository

import com.rakuten.demo.BuildConfig
import com.rakuten.demo.data.api.IRakutenApiService
import com.rakuten.demo.data.model.RecentPhotos
import com.rakuten.demo.util.Constants
import com.rakuten.demo.util.NetworkResult
import javax.inject.Inject

class RecentPhotosRepositoryImpl @Inject constructor(
    private val iRakutenApiService: IRakutenApiService,
) : IRecentPhotosRepository {

    override suspend fun getRecentPhotos(): NetworkResult<RecentPhotos> {
        try {
            val response = iRakutenApiService.getRecentPhotos(getQueryMap())
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { return NetworkResult.Success(it) }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun error(errorMessage: String): NetworkResult.Error =
        NetworkResult.Error("Api call failed $errorMessage")

    private fun getQueryMap(): Map<String, String> {
        val queryMap = mutableMapOf<String, String>()
        queryMap["method"] = Constants.METHOD
        queryMap["api_key"] = BuildConfig.API_KEY
        queryMap["page"] = Constants.PAGE
        queryMap["format"] = Constants.FORMAT
        queryMap["nojsoncallback"] = Constants.NO_JSON_CALLBACK
        queryMap["safe_search"] = Constants.SAFE_SEARCH
        return queryMap
    }
}