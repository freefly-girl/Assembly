package com.educational.assemblyapp.presentation.filtersScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educational.assemblyapp.domain.StoryblocksRepository
import com.educational.assemblyapp.domain.entity.FrameRateTypeEnum
import com.educational.assemblyapp.domain.entity.VideoQualityTypeEnum
import com.educational.assemblyapp.domain.entity.VideoSearch
import com.educational.assemblyapp.presentation.common.launchWithErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FiltersViewModel @Inject constructor(
    private val repository: StoryblocksRepository
) : ViewModel() {

    private val _videoQualityState = MutableLiveData(VideoQualityTypeEnum.ALL)
    val videoQualityState: LiveData<VideoQualityTypeEnum> = _videoQualityState

    private val _frameRates = MutableLiveData(FrameRateTypeEnum.FPS_25)
    val frameRates: LiveData<FrameRateTypeEnum> = _frameRates

    private val _screenState = MutableLiveData<FiltersState>(FiltersState.Initial())
    val screenState: LiveData<FiltersState> = _screenState

    fun onClickedGoToResults(keywords: String, quality: String, fps: String) {
        viewModelScope.launchWithErrorHandler(block = {
            _screenState.value = FiltersState.Loading()
            var result = repository.search(
                keywords = keywords,
                qualityType = quality,
                fps = fps
            )
            _screenState.value = FiltersState.Success(result)
        }, onError = {
            _screenState.value = FiltersState.Error(it)
        })
    }

}

sealed class FiltersState {
    class Initial() : FiltersState()
    class Loading() : FiltersState()
    class Success(val result: List<VideoSearch>) : FiltersState()
    class Error(val throwable: Throwable) : FiltersState()
}