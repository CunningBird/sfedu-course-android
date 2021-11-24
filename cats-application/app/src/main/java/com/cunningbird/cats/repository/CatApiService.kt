package com.cunningbird.cats.repository

import com.cunningbird.cats.model.CatImageModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {

    @GET("v1/images/search")
    suspend fun getCatImages(@Query("page") page: Int, @Query("limit") size: Int): List<CatImageModel>
}