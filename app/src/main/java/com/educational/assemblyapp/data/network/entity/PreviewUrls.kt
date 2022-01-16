package com.educational.assemblyapp.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreviewUrls(
    @SerialName("_180p")
    val _180p: String?,
    @SerialName("_360p")
    val _360p: String?,
    @SerialName("_480p")
    val _480p: String?,
    @SerialName("_720p")
    val _720p: String?
)