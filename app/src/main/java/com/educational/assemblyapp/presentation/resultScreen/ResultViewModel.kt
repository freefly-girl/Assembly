package com.educational.assemblyapp.presentation.resultScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educational.assemblyapp.domain.AssemblyRepository
import com.educational.assemblyapp.domain.StoryblocksRepository
import com.educational.assemblyapp.domain.entity.FrameRateTypeEnum
import com.educational.assemblyapp.domain.entity.Video
import com.educational.assemblyapp.domain.entity.VideoQualityTypeEnum
import com.educational.assemblyapp.domain.entity.VideoSearch
import com.educational.assemblyapp.presentation.common.launchWithErrorHandler
import com.educational.assemblyapp.presentation.resultScreen.ResultState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val storyblocksRepository: StoryblocksRepository,
    private val assemblyRepository: AssemblyRepository
    ) : ViewModel() {

    private val repository: AssemblyRepository
    private val _videoState = MutableLiveData<ResultState>(Loading())
    val videoState: LiveData<ResultState> = _videoState

    init {
//        viewModelScope.launchWithErrorHandler(block = {
//            val video: List<VideoSearch> = storyblocksRepository.search(
//                keywords = ,
//                VideoQualityTypeEnum.ALL,
//                FrameRateTypeEnum.FPS_25
//            )
//            _videoState.value = SuccessStoryblocks(video)
//        }, onError = {
//            _videoState.value = Error(it)
//        })
        repository = assemblyRepository
    }

    fun onButtonClicked(video: VideoSearch) {
        viewModelScope.launchWithErrorHandler(block = {
            // TODO: new function from StoryblocksRepository
        })
    }

    fun onFinishingButtonClicked(video: Video) {
        var varVideo = video
        viewModelScope.launchWithErrorHandler(block = {
            varVideo.previewUrl = "https://d2v9y0dukr6mq2.cloudfront.net/video/thumbnail/slow-motion-falling-money_-jjatxweb__S0000.jpg"
            varVideo.stockItemId = "11851"
            varVideo = repository.updateVideoByTitle(video = varVideo)
            val jobId = repository.assembleVideo(varVideo.title)
            Log.d("assembly", "started assembling $jobId")
            _videoState.value = Success(varVideo)
        })
    }
}

sealed class ResultState {
    class Loading() : ResultState()
    class SuccessStoryblocks(val video: List<VideoSearch>) : ResultState()
    class Error(val throwable: Throwable) : ResultState()
    class Success(val video: Video) : ResultState()
}