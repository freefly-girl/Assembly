package com.educational.assemblyapp.presentation.mainMenu

import com.educational.assemblyapp.domain.entity.Video

sealed class MainMenuState {
    class Loading: MainMenuState()
    class Success(val videos: List<Video>): MainMenuState()
    class Error(val throwable: Throwable): MainMenuState()
}