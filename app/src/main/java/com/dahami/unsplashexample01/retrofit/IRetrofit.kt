package com.dahami.unsplashexample01.retrofit

import com.dahami.unsplashexample01.utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {


    @GET(API.SEARCH_PHOTO)
    fun searchPhotos(@Query("query") searchTerm: String) : Call<JsonElement>

    @GET(API.SEARCH_USERS)
    fun searchUses(@Query("query") searchTerm: String) : Call<JsonElement>

}