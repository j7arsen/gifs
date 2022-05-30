package com.example.testtask.data.api

import com.example.testtask.data.api.response.GifsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GifService {

    @GET("v1/gifs/search")
    suspend fun getGifs(@QueryMap params: Map<String, String>): Response<GifsResponse>
}
