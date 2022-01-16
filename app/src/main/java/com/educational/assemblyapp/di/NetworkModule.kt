package com.educational.assemblyapp.di

import com.educational.assemblyapp.data.network.AssemblyApi
import com.educational.assemblyapp.data.network.AssemblyRepositoryImpl
import com.educational.assemblyapp.data.network.StoryblocksAPI
import com.educational.assemblyapp.data.network.StoryblocksRepositoryImpl
import com.educational.assemblyapp.domain.AssemblyRepository
import com.educational.assemblyapp.domain.StoryblocksRepository
import com.educational.assemblyapp.presentation.common.SocketHandler
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.Socket
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {

        @Provides
        fun provideAssemblyApi(): AssemblyApi = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8888")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                    .build()
            )
            .addConverterFactory(
                Json(builderAction = {
                    isLenient = true
                    ignoreUnknownKeys = true
                }).asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create()

        @Provides
        fun provideSocketIO(): Socket {
            SocketHandler.setSocket()
            SocketHandler.establishConnection()
            val mSocket = SocketHandler.getSocket()
            //mSocket.emit("join_room")
            return mSocket
        }

        @Provides
        fun provideStoryblocksAPI(): StoryblocksAPI = Retrofit.Builder()
            .baseUrl("https://api.videoblocks.com")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                    .build()
            )
            .addConverterFactory(
                Json(builderAction = {
                    isLenient = true
                    ignoreUnknownKeys = true
                }).asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create()
    }

    @Binds
    abstract fun getRepository(assemblyRepositoryImpl: AssemblyRepositoryImpl): AssemblyRepository

    @Binds
    abstract fun getStoryblocksRepository(storyblocksRepositoryImpl: StoryblocksRepositoryImpl): StoryblocksRepository
}