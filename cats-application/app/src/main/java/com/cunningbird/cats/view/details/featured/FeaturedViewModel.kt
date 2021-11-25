package com.cunningbird.cats.view.details.featured

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.cunningbird.cats.data.repository.CatImagesRepository
import com.cunningbird.cats.model.details.CatFavorite

@ExperimentalPagingApi
class FeaturedViewModel(private val repository: CatImagesRepository = CatImagesRepository.getInstance()) : ViewModel() {

    suspend fun getFavoriteInfo(id: String): CatFavorite {
        return repository.getFavoritesCatImage(id)
    }
}