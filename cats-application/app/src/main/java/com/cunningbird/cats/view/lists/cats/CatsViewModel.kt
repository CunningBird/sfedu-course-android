package com.cunningbird.cats.view.lists.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cunningbird.cats.data.repository.CatImagesRepository
import com.cunningbird.cats.model.lists.CatListItem
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class CatsViewModel(private val repository: CatImagesRepository = CatImagesRepository.getInstance()) : ViewModel() {

    fun fetchCatImages(): Flow<PagingData<CatListItem>> {
        return repository.getCatImages().cachedIn(viewModelScope)
    }

    suspend fun addFeatured(id: String, subId: String): String {
        return repository.addFavoritesCatImage(id, subId)
    }
}