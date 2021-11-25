package com.cunningbird.cats.view.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cunningbird.cats.data.CatImagesRepository
import com.cunningbird.cats.model.CatImageModel
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class CatsViewModel(private val repository: CatImagesRepository = CatImagesRepository.getInstance()) : ViewModel() {

    fun fetchCatImages(): Flow<PagingData<CatImageModel>> {
        return repository.letCatImagesFlow().cachedIn(viewModelScope)
    }
}