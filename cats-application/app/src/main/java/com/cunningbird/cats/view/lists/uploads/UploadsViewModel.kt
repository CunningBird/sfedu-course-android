package com.cunningbird.cats.view.lists.uploads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cunningbird.cats.data.repository.CatImagesRepository
import com.cunningbird.cats.model.lists.UploadedCatListItem
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class UploadsViewModel(private val repository: CatImagesRepository = CatImagesRepository.getInstance()) : ViewModel() {

    fun fetchCatImages(): Flow<PagingData<UploadedCatListItem>> {
        return repository.getUploadedCatImages().cachedIn(viewModelScope)
    }
}