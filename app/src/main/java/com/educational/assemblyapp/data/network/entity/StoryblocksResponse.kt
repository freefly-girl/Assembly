package com.educational.assemblyapp.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryblocksResponse(
    @SerialName("results")
    val results: List<Result>?,
    @SerialName("total_results")
    val totalResults: Int?
)