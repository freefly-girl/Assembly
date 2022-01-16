package com.educational.assemblyapp.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssemblySendBody(
    @SerialName("title")
    val title: String
)