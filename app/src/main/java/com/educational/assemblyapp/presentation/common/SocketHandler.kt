package com.educational.assemblyapp.presentation.common
import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {

    private lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket("http://10.0.2.2:8888/assembly")
        } catch (e: URISyntaxException) {
            Log.e("SocketIO", "error on creating socket")

        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
        Log.e("SocketIO", "socket connected")
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
        Log.e("SocketIO", "socket disconnected")
    }
}