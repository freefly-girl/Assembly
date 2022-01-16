package com.educational.assemblyapp.domain.entity;

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Video (
        val title: String,
        var stockItemId: String = "",
        val text: String,
        val searchReq: String = "",
        var previewUrl: String = ""
) : Parcelable