package com.cunningbird.cats.view.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.cunningbird.cats.data.CatImagesRepository
import com.cunningbird.cats.model.CatImageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalPagingApi
class CatsViewModel(private val repository: CatImagesRepository = CatImagesRepository.getInstance()) : ViewModel() {

    fun fetchCatImages(): Flow<PagingData<CatImageModel>> { // TODO Provide model instead string
        return repository.letCatImagesFlow().cachedIn(viewModelScope)
    }
}