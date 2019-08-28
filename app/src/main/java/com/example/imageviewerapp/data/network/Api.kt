package com.example.imageviewerapp.data.network

import com.example.imageviewerapp.data.model.Images
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("character/")
    fun getImages(
        @Query("count") count: Int,
        @Query("page") page: Int
    ): Single<Images>
}