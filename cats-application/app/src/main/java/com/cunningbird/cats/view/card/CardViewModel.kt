package com.cunningbird.cats.view.card

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.cunningbird.cats.data.CatImagesRepository
import com.cunningbird.cats.model.CatImageModel

@ExperimentalPagingApi
class CardViewModel(private val repository: CatImagesRepository = CatImagesRepository.getInstance()) : ViewModel() {

    fun getCatInfo(id: String): CatImageModel {
        return repository.getCatImage(id)
    }
}