package com.educational.assemblyapp.presentation.resultScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educational.assemblyapp.domain.StoryblocksRepository
import com.educational.assemblyapp.domain.entity.FrameRateTypeEnum
import com.educational.assemblyapp.domain.entity.VideoQualityTypeEnum
import com.educational.assemblyapp.domain.entity.VideoSearch
import com.educational.assemblyapp.presentation.common.launchWithErrorHandler
import com.educational.assemblyapp.presentation.resultScreen.ResultState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val storyblocksRepository: StoryblocksRepository
    ) : ViewModel() {

    private val _videoState = MutableLiveData<ResultState>(Loading())
    val videoState: LiveData<ResultState> = _videoState

    init {
        viewModelScope.launchWithErrorHandler(block = {
            val video: List<VideoSearch> = storyblocksRepository.search(
                null,
                VideoQualityTypeEnum.ALL,
                FrameRateTypeEnum.FPS_25
            )
            _videoState.value = Success(video)
        }, onError = {
            _videoState.value = Error(it)
        })
    }

    fun onButtonClicked(video: VideoSearch) {
        viewModelScope.launchWithErrorHandler(block = {
            // TODO: new function from StoryblocksRepository
        })
    }
}

sealed class ResultState {
    class Loading() : ResultState()
    class Success(val video: List<VideoSearch>) : ResultState()
    class Error(val throwable: Throwable) : ResultState()
}