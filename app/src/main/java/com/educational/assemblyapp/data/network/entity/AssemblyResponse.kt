package com.educational.assemblyapp.data.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssemblyResponse (
    @SerialName("id")
    val id: String
)
