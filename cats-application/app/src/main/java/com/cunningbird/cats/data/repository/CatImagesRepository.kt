package com.cunningbird.cats.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cunningbird.cats.data.source.CatApiService
import com.cunningbird.cats.data.source.RemoteInjector
import com.cunningbird.cats.model.AddFavorite
import com.cunningbird.cats.model.CatFavorite
import com.cunningbird.cats.model.CatImage
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class CatImagesRepository(private val catApiService: CatApiService = RemoteInjector.injectCatApiService()) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        fun getInstance() = CatImagesRepository()
    }


    fun letCatImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CatImage>> {
        return Pager(config = pagingConfig, pagingSourceFactory = { CatImageList(catApiService) }).flow
    }

    fun getCatImage(id: String): CatImage { // TODO Получение конкретного кота
        return CatImage(id, "url")
    }

    suspend fun addCatImageAsFavorites(id: String): String { // TODO Добавление кота в избранное
        val request = AddFavorite(image_id = id)
        return catApiService.addFavorite(request).message
    }


    fun letCatFeaturedImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CatFavorite>> {
        return Pager(config = pagingConfig, pagingSourceFactory = { FavoritesCatImageList(catApiService) }).flow
    }

    fun getFeaturedCatImage(id: String): CatImage { // TODO Получение конкретного избранного
        return CatImage(id, "url")
    }

    // TODO Удаление кота из избранного


    fun letCatUploadsImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CatImage>> {
        return Pager(config = pagingConfig, pagingSourceFactory = { UploadedCatImageList(catApiService) }).flow
    }

    fun getUploadedCatImage(id: String): CatImage { // TODO Получение конкретного загруженного
        return CatImage(id, "url")
    }

    // TODO Загрузка изображения


    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }
}