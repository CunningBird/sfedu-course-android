package com.cunningbird.cats.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cunningbird.cats.data.paging.CatFavoritesImagePagingSource
import com.cunningbird.cats.data.paging.CatImagePagingSource
import com.cunningbird.cats.data.paging.CatUploadImagePagingSource
import com.cunningbird.cats.model.CatImageModel
import com.cunningbird.cats.repository.CatApiService
import com.cunningbird.cats.repository.RemoteInjector
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class CatImagesRepository(private val catApiService: CatApiService = RemoteInjector.injectCatApiService()) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        fun getInstance() = CatImagesRepository()
    }

    fun letCatImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CatImageModel>> {
        return Pager(config = pagingConfig, pagingSourceFactory = { CatImagePagingSource(catApiService) }).flow
    }

    // TODO Получение конкретного кота
    fun getCatImage(id: String): CatImageModel {
        return CatImageModel(id, "url") // TODO Get CatImageModel from CatApiService
    }

    fun letCatFeaturedImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CatImageModel>> {
        return Pager(config = pagingConfig, pagingSourceFactory = { CatFavoritesImagePagingSource(catApiService) }).flow
    }

    // TODO Получение конкретного избранного
    fun getFeaturedCatImage(id: String): CatImageModel {
        return CatImageModel(id, "url")// TODO Get CatImageModel from CatApiService
    }

    fun letCatUploadsImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CatImageModel>> {
        return Pager(config = pagingConfig, pagingSourceFactory = { CatUploadImagePagingSource(catApiService) }).flow
    }

    // TODO Получение конкретного загруженного
    fun getUploadedCatImage(id: String): CatImageModel {
        return CatImageModel(id, "url")// TODO Get CatImageModel from CatApiService
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }
}