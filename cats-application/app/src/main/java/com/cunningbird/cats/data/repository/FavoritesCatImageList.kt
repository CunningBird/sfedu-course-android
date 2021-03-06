package com.cunningbird.cats.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cunningbird.cats.data.repository.CatImagesRepository.Companion.DEFAULT_PAGE_INDEX
import com.cunningbird.cats.data.source.CatApiService
import com.cunningbird.cats.model.lists.FavoriteCatListItem
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class FavoritesCatImageList(private val catApiService: CatApiService) : PagingSource<Int, FavoriteCatListItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteCatListItem> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = catApiService.getFavorites(page, params.loadSize)
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

    override fun getRefreshKey(state: PagingState<Int, FavoriteCatListItem>): Int {
        return 0
    }
}