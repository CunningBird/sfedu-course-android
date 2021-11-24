package com.cunningbird.cats.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cunningbird.cats.data.CatImagesRepository.Companion.DEFAULT_PAGE_INDEX
import com.cunningbird.cats.model.CatImageModel
import com.cunningbird.cats.repository.remote.CatApiService
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class CatImagePagingSource(private val catApiService: CatApiService) : PagingSource<Int, CatImageModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatImageModel> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = catApiService.getCatImages(page, params.loadSize)
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

    override fun getRefreshKey(state: PagingState<Int, CatImageModel>): Int? {
        TODO("Not yet implemented")
    }

}