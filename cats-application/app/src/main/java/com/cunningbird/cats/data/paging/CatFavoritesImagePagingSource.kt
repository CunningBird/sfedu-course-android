package com.cunningbird.cats.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cunningbird.cats.data.CatImagesRepository.Companion.DEFAULT_PAGE_INDEX
import com.cunningbird.cats.model.CatImage
import com.cunningbird.cats.repository.CatApiService
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class CatFavoritesImagePagingSource(private val catApiService: CatApiService) : PagingSource<Int, CatImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatImage> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = catApiService.getFavoritesCatImages(page, params.loadSize)
            LoadResult.Page(
                response, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatImage>): Int? {
        TODO("Not yet implemented")
    }
}