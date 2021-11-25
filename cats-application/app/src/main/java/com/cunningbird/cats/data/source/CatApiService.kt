package com.cunningbird.cats.data.source

import com.cunningbird.cats.model.*
import retrofit2.http.*

interface CatApiService {

    @GET("v1/images/search")
    suspend fun getPublicImages(@Query("page") page: Int, @Query("limit") size: Int): List<CatImage>

    @GET("v1/images/{image_id}")
    suspend fun getPublicImage(@Path("image_id") image_id: String): CatImage

    @GET("v1/images")
    suspend fun getUploadedImages(@Query("page") page: Int, @Query("limit") size: Int): List<CatImage>

    @POST("v1/images/upload")
    suspend fun getUploadCatImage(): CatImage


    @GET("v1/favourites")
    suspend fun getFavorites(@Query("page") page: Int, @Query("limit") size: Int): List<CatFavorite>

    @GET("v1/favourites/{favorite_id}")
    suspend fun getFavorite(@Path("favorite_id") favorite_id: String): CatFavorite

    @POST("v1/favourites")
    suspend fun addFavorite(@Body data: AddFavorite): PostFavoriteResponse

    @DELETE("v1/favourites")
    suspend fun removeFavorite(@Body data: RemoveFavorite): PostFavoriteResponse
}