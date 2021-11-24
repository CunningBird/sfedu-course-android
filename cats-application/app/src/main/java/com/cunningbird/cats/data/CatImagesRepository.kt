package com.cunningbird.cats.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import androidx.paging.rxjava2.observable
import com.cunningbird.cats.model.CatImageModel
import com.cunningbird.cats.repository.remote.CatApiService
import com.cunningbird.cats.repository.remote.RemoteInjector
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class CatImagesRepository(private val catApiService: CatApiService = RemoteInjector.injectCatApiService()) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        fun getInstance() = CatImagesRepository()
    }

    fun letCatImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CatImageModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { CatImagePagingSource(catApiService) }
        ).flow
    }

    fun letCatImagesObservable(pagingConfig: PagingConfig = getDefaultPageConfig()): Observable<PagingData<CatImageModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { CatImagePagingSource(catApiService) }
        ).observable
    }

    fun letCatImagesLiveData(pagingConfig: PagingConfig = getDefaultPageConfig()): LiveData<PagingData<CatImageModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { CatImagePagingSource(catApiService) }
        ).liveData
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }
}