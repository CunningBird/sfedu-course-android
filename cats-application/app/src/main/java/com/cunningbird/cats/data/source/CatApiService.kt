package com.cunningbird.cats.data.source

import com.cunningbird.cats.model.calls.AddFavorite
import com.cunningbird.cats.model.calls.StatusResponse
import com.cunningbird.cats.model.calls.RemoveImage
import com.cunningbird.cats.model.calls.UploadImage
import com.cunningbird.cats.model.details.CatAnalysis
import com.cunningbird.cats.model.details.CatFavorite
import com.cunningbird.cats.model.details.CatImage
import com.cunningbird.cats.model.lists.CatListItem
import com.cunningbird.cats.model.lists.FavoriteCatListItem
import com.cunningbird.cats.model.lists.UploadedCatListItem
import retrofit2.http.*

interface CatApiService {

    @GET("v1/images/search")
    suspend fun getPublicImages(@Query("page") page: Int, @Query("limit") size: Int, @Query("order") sort: String): List<CatListItem>

    @GET("v1/images")
    suspend fun getUploadedImages(@Query("page") page: Int, @Query("limit") size: Int): List<UploadedCatListItem>

    @GET("v1/favourites")
    suspend fun getFavorites(@Query("page") page: Int, @Query("limit") size: Int): List<FavoriteCatListItem>



    @GET("v1/images/{image_id}")
    suspend fun getPublicImage(@Path("image_id") image_id: String): CatImage

    @POST("v1/images/{image_id}/analysis")
    suspend fun getUploadCatImage(@Path("image_id") image_id: String): CatAnalysis

    @POST("v1/favourites")
    suspend fun addUploadCatImage(@Body data: UploadImage): StatusResponse

    @DELETE("v1/favourites")
    suspend fun removeUploadCatImage(@Body data: RemoveImage): StatusResponse

    @GET("v1/favourites/{favorite_id}")
    suspend fun getFavorite(@Path("favorite_id") favorite_id: String): CatFavorite

    @POST("v1/favourites")
    suspend fun addFavorite(@Body data: AddFavorite): StatusResponse

    @DELETE("v1/favourites/{favorite_id}")
    suspend fun removeFavorite(@Path("favorite_id") favorite_id: String): StatusResponse
}