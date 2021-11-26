package com.cunningbird.cats.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cunningbird.cats.data.source.CatApiService
import com.cunningbird.cats.data.source.RemoteInjector
import com.cunningbird.cats.model.calls.AddFavorite
import com.cunningbird.cats.model.calls.RemoveImage
import com.cunningbird.cats.model.calls.UploadImage
import com.cunningbird.cats.model.details.CatAnalysis
import com.cunningbird.cats.model.details.CatFavorite
import com.cunningbird.cats.model.details.CatImage
import com.cunningbird.cats.model.lists.CatListItem
import com.cunningbird.cats.model.lists.FavoriteCatListItem
import com.cunningbird.cats.model.lists.UploadedCatListItem
import kotlinx.coroutines.flow.Flow
import java.io.File

@ExperimentalPagingApi
class CatImagesRepository(private val catApiService: CatApiService = RemoteInjector.injectCatApiService()) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 5

        fun getInstance() = CatImagesRepository()
    }

    fun getCatImages(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CatListItem>> {
        return Pager(config = pagingConfig, pagingSourceFactory = { CatImageList(catApiService) }).flow
    }

    fun getUploadedCatImages(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<UploadedCatListItem>> {
        return Pager(config = pagingConfig, pagingSourceFactory = { UploadedCatImageList(catApiService) }).flow
    }

    fun getFavoritesCatImages(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<FavoriteCatListItem>> {
        return Pager(config = pagingConfig, pagingSourceFactory = { FavoritesCatImageList(catApiService) }).flow
    }

    suspend fun getCatImage(id: String): CatImage {
        return catApiService.getPublicImage(id)
    }

    suspend fun getFavoritesCatImage(id: String): CatFavorite {
        return catApiService.getFavorite(id)
    }

    suspend fun getUploadedCatImage(id: String): CatAnalysis {
        return catApiService.getUploadCatImage(id)
    }

    suspend fun addFavoritesCatImage(id: String, subId: String): String {
        val request = AddFavorite(image_id = id, sub_id = subId)
        return catApiService.addFavorite(request).message
    }

    suspend fun removeFavoritesCatImage(id: String): String {
        return catApiService.removeFavorite(id).message
    }

    suspend fun addUploadedCatImage(file: File, subId: String): String {
        val request = UploadImage(file = file, sub_id = subId)
        return catApiService.addUploadCatImage(request).message
    }

    suspend fun removeUploadedCatImage(id: String): String {
        val request = RemoveImage(image_id = id)
        return catApiService.removeUploadCatImage(request).message
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }
}