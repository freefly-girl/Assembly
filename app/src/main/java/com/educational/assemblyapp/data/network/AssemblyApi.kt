package com.educational.assemblyapp.data.network

import com.educational.assemblyapp.data.network.entity.AssemblyResponse
import com.educational.assemblyapp.data.network.entity.AssemblySendBody
import com.educational.assemblyapp.data.network.entity.VideoResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AssemblyApi {
    @GET("/videos/{title}")
    suspend fun getVideoByTitle(@Path(value = "title", encoded = true) title: String ): VideoResponse

    @PUT("/videos/{title}")
    suspend fun updateVideoByTitle(@Path(value = "title", encoded = true) title: String, @Body body: VideoResponse): VideoResponse

    @GET("/videos")
    suspend fun getVideos(): List<VideoResponse>

    @POST("/videos")
    suspend fun addVideo(@Body body: VideoResponse): VideoResponse

    @POST("/assembly")
    suspend fun assemblyVideo(@Body body: AssemblySendBody): AssemblyResponse

    @GET("/init")
    suspend fun init(): ResponseBody

    @Streaming
    @GET("/download")
    fun downloadVideo(@Query("title") title: String): Call<ResponseBody>
}