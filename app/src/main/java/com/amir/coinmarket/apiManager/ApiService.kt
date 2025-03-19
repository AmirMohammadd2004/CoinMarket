package com.amir.coinmarket.apiManager

import ir.dunijet.dunipool.apiManager.model.chart_data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers(KEY_URL)
    @GET("v2/news/")
    fun get_news_top(
        @Query("sortOrder") sortOrder: String = "popular",
    ): Call<news_top_json>


    @Headers(KEY_URL)
    @GET("top/totaltoptiervolfull")
    fun get_coin_top(
        @Query("tsym") tsym_get: String = "USD",
        @Query("limit") limit: Int = 10,
    ): Call<coin_top_json>


    @Headers(KEY_URL)
    @GET("{period}")
    fun get_Chart_Data(
        @Path("period") period: String,
        @Query("fsym") fsym: String,
        @Query("limit") limit: Int,
        @Query("aggregate") aggregate: Int,
        @Query("tsym") tsym: String = "USD",

        ): Call<chart_data>

}