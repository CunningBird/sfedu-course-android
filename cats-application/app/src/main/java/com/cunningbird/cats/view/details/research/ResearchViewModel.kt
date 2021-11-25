package com.cunningbird.cats.view.details.research

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.cunningbird.cats.data.repository.CatImagesRepository
import com.cunningbird.cats.model.details.CatAnalysis

@ExperimentalPagingApi
class ResearchViewModel(private val repository: CatImagesRepository = CatImagesRepository.getInstance()) : ViewModel() {

    suspend fun getUploadedCatInfo(id: String): CatAnalysis {
        return repository.getUploadedCatImage(id)
    }
}