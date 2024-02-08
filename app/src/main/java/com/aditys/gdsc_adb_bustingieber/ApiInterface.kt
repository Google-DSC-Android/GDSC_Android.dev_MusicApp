package com.aditys.gdsc_adb_bustingieber


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @Headers("X-RapidAPI-Key:6b5dfcce33msh06e06ec4af4f8b2p18a89ejsndad351702d1d",
        "X-RapidAPI-Host:deezerdevs-deezer.p.rapidapi.com")
    @GET("search")
    fun getData(@Query("q")query: String): Call<MyData>
}
