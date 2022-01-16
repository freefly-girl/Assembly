package com.educational.assemblyapp.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("duration")
    val duration: Int?,
    @SerialName("id")
    val id: Int?,
    @SerialName("is_new")
    val isNew: Boolean?,
    @SerialName("preview_urls")
    val previewUrls: PreviewUrls?,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("type")
    val type: String?
)