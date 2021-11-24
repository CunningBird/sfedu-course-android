package com.cunningbird.cats.repository

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RemoteInjector {

    private const val API_ENDPOINT = "https://api.thecatapi.com"
    const val API_KEY = "8de421de-4a65-413c-a0f3-de6c4b50b252"
    const val HEADER_API_KEY = "x-api-key"

    fun injectCatApiService(retrofit: Retrofit = getRetrofit()): CatApiService {
        return retrofit.create(CatApiService::class.java)
    }

    private fun getRetrofit(okHttpClient: OkHttpClient = getOkHttpClient()): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private fun getOkHttpNetworkInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest =
                    chain.request().newBuilder().addHeader(HEADER_API_KEY, API_KEY).build()
                return chain.proceed(newRequest)
            }
        }
    }

    private fun getHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun getOkHttpClient(okHttpLogger: HttpLoggingInterceptor = getHttpLogger(), okHttpNetworkInterceptor: Interceptor = getOkHttpNetworkInterceptor()): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(okHttpLogger)
            .addInterceptor(okHttpNetworkInterceptor)
            .build()
    }
}