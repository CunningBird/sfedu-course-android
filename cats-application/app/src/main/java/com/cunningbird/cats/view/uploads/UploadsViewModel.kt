package com.cunningbird.cats.view.uploads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cunningbird.cats.data.repository.CatImagesRepository
import com.cunningbird.cats.model.CatImage
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class UploadsViewModel(private val repository: CatImagesRepository = CatImagesRepository.getInstance()) : ViewModel() {

    fun fetchCatImages(): Flow<PagingData<CatImage>> {
        return repository.letCatUploadsImagesFlow().cachedIn(viewModelScope)
    }
}