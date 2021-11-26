package com.cunningbird.cats.view.lists.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cunningbird.cats.data.repository.CatImagesRepository
import com.cunningbird.cats.model.lists.FavoriteCatListItem
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class FavoritesViewModel(private val repository: CatImagesRepository = CatImagesRepository.getInstance()) : ViewModel() {

    fun fetchCatImages(): Flow<PagingData<FavoriteCatListItem>> {
        return repository.getFavoritesCatImages().cachedIn(viewModelScope)
    }

    suspend fun removeCatImage(id: String): String {
        return repository.removeFavoritesCatImage(id)
    }
}