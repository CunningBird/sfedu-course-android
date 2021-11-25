package com.cunningbird.cats.repository

import com.cunningbird.cats.model.CatImage
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CatApiService {

    @GET("v1/images/search")
    suspend fun getCatImages(@Query("page") page: Int, @Query("limit") size: Int): List<CatImage>

    //@GET("v1/images/search")
    //suspend fun getCatImage(@Query("page") page: Int, @Query("limit") size: Int): CatImage

    // TODO Добавление в избранные
    //@POST("v1/images/search")
    //suspend fun addCatImageAsFavorites(@Body("image_id") image_id: String, @Body("sub_id") sub_id: String): CatImage


    @GET("v1/favourites")
    suspend fun getFavoritesCatImages(@Query("page") page: Int, @Query("limit") size: Int): List<CatImage>

    //@GET("v1/images/search")
    //suspend fun getFavoritesCatImage(@Query("page") page: Int, @Query("limit") size: Int): CatImage

    // TODO Удаление из избранного


    @GET("v1/images")
    suspend fun getUploadCatImages(@Query("page") page: Int, @Query("limit") size: Int): List<CatImage>

    //@GET("v1/images/search")
    //suspend fun getUploadCatImage(@Query("page") page: Int, @Query("limit") size: Int): CatImage

    // TODO Загрузка изображения
}