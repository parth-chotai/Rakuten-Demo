package com.rakuten.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rakuten.demo.data.repository.IRecentPhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentPhotosViewModel @Inject constructor(
    private val iRecentPhotosRepository: IRecentPhotosRepository,
) : ViewModel() {

    init {
        getRecentPhotos()
    }

    private fun getRecentPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val networkResult = iRecentPhotosRepository.getRecentPhotos()
            println(networkResult)
        }
    }
}