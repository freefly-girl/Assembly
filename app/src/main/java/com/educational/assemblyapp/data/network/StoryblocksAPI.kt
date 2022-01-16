package com.educational.assemblyapp.data.network

import com.educational.assemblyapp.data.network.entity.StoryblocksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StoryblocksAPI {

    @GET("/api/v2/videos/search")
    suspend fun search(
        @Query("apikey") apikey: String = "test_f335c1de1a4afde65cf0502312b77c10540701e453601ad8788faf8fddf",
        @Query("expires") expires: String,
        @Query("hmac") hmac: String,
        @Query("projectId") projectId: Int = 1,
        @Query("userId") userId: Int = 1,
        @Query("keywords") keywords: String?,
        @Query("quality") quality: String,
        @Query("frameRates") frameRates: String?,
    ): StoryblocksResponse
}
