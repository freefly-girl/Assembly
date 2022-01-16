package com.educational.assemblyapp.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    @SerialName("search_req")
    val searchReq: String? = null,
    @SerialName("stock_item_id")
    val stockItemId: String? = null,
    @SerialName("text")
    val text: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("preview_url")
    val previewUrl: String? = null
)