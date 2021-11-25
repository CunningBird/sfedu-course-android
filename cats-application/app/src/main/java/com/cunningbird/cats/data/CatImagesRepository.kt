package com.cunningbird.cats.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cunningbird.cats.data.paging.CatFavoritesImagePagingSource
import com.cunningbird.cats.data.paging.CatImagePagingSource
import com.cunningbird.cats.data.paging.CatUploadImagePagingSource
import com.cunningbird.cats.model.CatImage
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

    fun letCatImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CatImage>> {
        return Pager(config = pagingConfig, pagingSourceFactory = { CatImagePagingSource(catApiService) }).flow
    }

    fun getCatImage(id: String): CatImage { // TODO Получение конкретного кота
        return CatImage(id, "url")
    }

    fun addCatImageAsFavorites(id: String): String { // TODO Добавление кота в избранное
        return "Cat Added To Favorites"
    }



    fun letCatFeaturedImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CatImage>> {
        return Pager(config = pagingConfig, pagingSourceFactory = { CatFavoritesImagePagingSource(catApiService) }).flow
    }

    fun getFeaturedCatImage(id: String): CatImage { // TODO Получение конкретного избранного
        return CatImage(id, "url")
    }



    fun letCatUploadsImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CatImage>> {
        return Pager(config = pagingConfig, pagingSourceFactory = { CatUploadImagePagingSource(catApiService) }).flow
    }

    fun getUploadedCatImage(id: String): CatImage { // TODO Получение конкретного загруженного
        return CatImage(id, "url")
    }



    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }
}