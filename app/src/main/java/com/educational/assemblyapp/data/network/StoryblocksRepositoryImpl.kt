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
        qualityType: VideoQualityTypeEnum,
        fps: FrameRateTypeEnum
    ): List<VideoSearch> {
        return storyblocksAPI.search(
            apikey = "",
            expires = "",
            hmac = "",
            projectId = 1,
            userId = 2,
            keywords = null,
            quality = qualityType.quality,
            frameRates = fps.value
        ).toVideo()
    }

    private fun StoryblocksResponse.toVideo() : List<VideoSearch> =
        results?.mapNotNull { result ->
            VideoSearch(
                title = result.title ?: return@mapNotNull null,
                thumbnailUrl = result.thumbnailUrl ?: return@mapNotNull null,
                previewUrls = (result.previewUrls ?: "").toString()
            )
        } ?: emptyList()
}
