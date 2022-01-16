package com.educational.assemblyapp.domain

import com.educational.assemblyapp.domain.entity.Video

interface AssemblyRepository {
    suspend fun getVideoByTitle(title: String): Video
    suspend fun updateVideoByTitle(video: Video): Video
    suspend fun getVideos(): List<Video>
    suspend fun addVideo(title: String, text: String): Video
    suspend fun assembleVideo(title: String): String
    fun downloadFile(video: Video)
    suspend fun init()
}