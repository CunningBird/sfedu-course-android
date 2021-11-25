package com.cunningbird.cats.repository

import com.cunningbird.cats.model.CatImageModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {

    @GET("v1/images/search")
    suspend fun getCatImages(@Query("page") page: Int, @Query("limit") size: Int): List<CatImageModel>

    //@GET("v1/images/search")
    //suspend fun getCatImage(@Query("page") page: Int, @Query("limit") size: Int): CatImageModel

    // TODO Добавление в избранные


    @GET("v1/favourites")
    suspend fun getFavoritesCatImages(@Query("page") page: Int, @Query("limit") size: Int): List<CatImageModel>

    //@GET("v1/images/search")
    //suspend fun getFavoritesCatImage(@Query("page") page: Int, @Query("limit") size: Int): CatImageModel

    // TODO Удаление из избранного


    @GET("v1/images")
    suspend fun getUploadCatImages(@Query("page") page: Int, @Query("limit") size: Int): List<CatImageModel>

    //@GET("v1/images/search")
    //suspend fun getUploadCatImage(@Query("page") page: Int, @Query("limit") size: Int): CatImageModel

    // TODO Загрузка изображения
}