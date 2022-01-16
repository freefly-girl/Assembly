package com.educational.assemblyapp.data.network

import com.educational.assemblyapp.data.network.entity.StoryblocksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StoryblocksAPI {

    @GET("/api/v2/videos/search")
    suspend fun search(
        @Query("APIKEY") apikey: String = "test_f335c1de1a4afde65cf0502312b77c10540701e453601ad8788faf8fddf",
        @Query("EXPIRES") expires: String = "1642459313",
        @Query("HMAC") hmac: String = "3bf567873d5041fb9006be4496880b773ab72a4f423185bcf83a779580673da6",
        @Query("project_id") projectId: Int = 1,
        @Query("user_id") userId: Int = 1,
        @Query("keywords") keywords: String?,
        @Query("quality") quality: String,
        @Query("frame_rates") frameRates: String?,
    ): StoryblocksResponse
}
