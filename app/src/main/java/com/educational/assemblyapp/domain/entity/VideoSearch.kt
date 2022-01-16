package com.educational.assemblyapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoSearch(
    val title: String,
    val thumbnailUrl: String,
    val previewUrls: String
) : Parcelable
