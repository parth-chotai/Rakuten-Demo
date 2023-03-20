package com.rakuten.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rakuten.demo.data.model.PhotosMetadata
import com.rakuten.demo.data.repository.IRecentPhotosRepository
import com.rakuten.demo.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentPhotosViewModel @Inject constructor(
    private val iRecentPhotosRepository: IRecentPhotosRepository,
) : ViewModel() {

    //region Variables
    private val _photosMetadata =
        MutableLiveData<NetworkResult<PhotosMetadata>>(NetworkResult.Loading)
    val photosMetadata: LiveData<NetworkResult<PhotosMetadata>> = _photosMetadata
    //endregion

    init {
        getRecentPhotos()
    }

    private fun getRecentPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val networkResult = iRecentPhotosRepository.getRecentPhotos()) {
                is NetworkResult.Error -> {
                    _photosMetadata.postValue(NetworkResult.Error(networkResult.message))
                }
                is NetworkResult.Success -> {
                    _photosMetadata.postValue(NetworkResult.Success(networkResult.data))
                }
                else -> Unit
            }
        }
    }
}