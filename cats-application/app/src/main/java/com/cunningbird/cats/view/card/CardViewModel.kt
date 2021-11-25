package com.cunningbird.cats.view.card

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.cunningbird.cats.data.repository.CatImagesRepository
import com.cunningbird.cats.model.CatImage

@ExperimentalPagingApi
class CardViewModel(private val repository: CatImagesRepository = CatImagesRepository.getInstance()) : ViewModel() {

    fun getCatInfo(id: String): CatImage {
        return repository.getCatImage(id)
    }
}