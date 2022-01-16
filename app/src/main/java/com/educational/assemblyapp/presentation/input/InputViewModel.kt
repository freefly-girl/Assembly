package com.educational.assemblyapp.presentation.input

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educational.assemblyapp.domain.AssemblyRepository
import com.educational.assemblyapp.domain.entity.Video
import com.educational.assemblyapp.presentation.common.launchWithErrorHandler
import com.educational.assemblyapp.presentation.filtersScreen.FiltersState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    assemblyRepository: AssemblyRepository
): ViewModel() {

    private val _screenState = MutableLiveData<InputScreenState>(InputScreenState.Initial())
    val screenState: LiveData<InputScreenState> = _screenState

    private val repository = assemblyRepository

    fun onGoToFiltersClicked(title: String, text: String) {
        viewModelScope.launchWithErrorHandler(block = {
            _screenState.value = InputScreenState.Loading()
            var video = repository.addVideo(title = title, text = text)

            _screenState.value = InputScreenState.Success(video)
        }, onError = {
            _screenState.value = InputScreenState.Error(it)
        })
    }

    fun setInitialScreenState() {
        _screenState.value = InputScreenState.Initial()
    }
}

sealed class InputScreenState {
    class Initial() : InputScreenState()
    class Loading() : InputScreenState()
    class Success(val videoProject: Video) : InputScreenState()
    class Error(val throwable: Throwable) : InputScreenState()
}