package com.cunningbird.cats.view.research

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.cunningbird.cats.data.CatImagesRepository
import com.cunningbird.cats.model.CatImage

@ExperimentalPagingApi
class ResearchViewModel(private val repository: CatImagesRepository = CatImagesRepository.getInstance()) : ViewModel() {

    fun getUploadedCatInfo(id: String): CatImage {
        return repository.getUploadedCatImage(id)
    }
}