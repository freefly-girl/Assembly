package com.educational.assemblyapp.presentation.mainMenu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educational.assemblyapp.domain.AssemblyRepository
import com.educational.assemblyapp.domain.entity.Video
import com.educational.assemblyapp.presentation.common.launchWithErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    assemblyRepository: AssemblyRepository,
    socket: Socket
): ViewModel() {

    private val _mainMenuState = MutableLiveData<MainMenuState>(MainMenuState.Loading())
    val mainMenuState: LiveData<MainMenuState> = _mainMenuState

    private val repository: AssemblyRepository = assemblyRepository
    private val mSocket: Socket = socket

    init {
        viewModelScope.launchWithErrorHandler(block = {
            repository.init()
        })
        viewModelScope.launchWithErrorHandler(block = {
            val videos: List<Video> = assemblyRepository.getVideos()
            _mainMenuState.value = MainMenuState.Success(videos)
        }, onError = {
            _mainMenuState.value = MainMenuState.Error(it)
        })
    }

    fun onFragmentResume() {
        _mainMenuState.value = MainMenuState.Loading()

        viewModelScope.launchWithErrorHandler(block = {
            val videos: List<Video> = repository.getVideos()
            _mainMenuState.value = MainMenuState.Success(videos)
        }, onError = {
            _mainMenuState.value = MainMenuState.Error(it)
        })
        mSocket.emit("join_room")
        mSocket.on("connect") {
            Log.e("SocketIO", "connected")
        }
        mSocket.on("msg") { args ->
            if (args[0] != null) {
                val counter = args[0] as String
                Log.e("SocketIO", counter)
            }
        }
    }

    fun onVideoClicked(video: Video) {
        viewModelScope.launchWithErrorHandler(block = {
            repository.downloadFile(video)
        })
    }
}
