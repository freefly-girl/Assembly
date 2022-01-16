package com.educational.assemblyapp.domain

import com.educational.assemblyapp.domain.entity.FrameRateTypeEnum
import com.educational.assemblyapp.domain.entity.VideoQualityTypeEnum
import com.educational.assemblyapp.domain.entity.VideoSearch

interface StoryblocksRepository {

    /**
     * Запрашивает видео согласно указанным пользователем фильтрам [VideoQualityTypeEnum] и [FrameRateTypeEnum]
     */
    suspend fun search(
        keywords: String?,
        qualityType: String,
        fps: String
    ) : List<VideoSearch>
}
