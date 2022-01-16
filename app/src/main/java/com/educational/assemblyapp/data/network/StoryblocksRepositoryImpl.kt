package com.educational.assemblyapp.data.network

import com.educational.assemblyapp.data.network.entity.StoryblocksResponse
import com.educational.assemblyapp.domain.StoryblocksRepository
import com.educational.assemblyapp.domain.entity.FrameRateTypeEnum
import com.educational.assemblyapp.domain.entity.VideoQualityTypeEnum
import com.educational.assemblyapp.domain.entity.VideoSearch
import javax.inject.Inject

class StoryblocksRepositoryImpl @Inject constructor(
    private val storyblocksAPI: StoryblocksAPI
) : StoryblocksRepository {

    override suspend fun search(
        keywords: String?,
        qualityType: String,
        fps: String
    ): List<VideoSearch> {
        return storyblocksAPI.search(
            projectId = 1,
            userId = 1,
            keywords = null,
            quality = qualityType,
            frameRates = fps
        ).toVideo()
    }

    private fun StoryblocksResponse.toVideo() : List<VideoSearch> =
        results?.mapNotNull { result ->
            VideoSearch(
                title = result.title ?: return@mapNotNull null,
                thumbnailUrl = result.thumbnailUrl ?: return@mapNotNull null
            )
        } ?: emptyList()
}
