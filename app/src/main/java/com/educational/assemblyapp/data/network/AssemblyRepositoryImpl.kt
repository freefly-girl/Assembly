package com.educational.assemblyapp.data.network

import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import com.educational.assemblyapp.data.network.entity.AssemblySendBody
import com.educational.assemblyapp.data.network.entity.VideoResponse
import com.educational.assemblyapp.domain.AssemblyRepository
import com.educational.assemblyapp.domain.entity.Video
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import javax.inject.Inject
import java.io.File

class AssemblyRepositoryImpl @Inject constructor(
    private val assemblyApi: AssemblyApi
): AssemblyRepository {
    override suspend fun getVideoByTitle(title: String): Video {
        return assemblyApi.getVideoByTitle(title = title).let {
            Video(
                title = it.title ?: "",
                stockItemId = it.stockItemId ?: "",
                text = it.text ?: "",
                searchReq = it.searchReq ?: "",
                previewUrl = it.previewUrl ?: ""
            )
        }
    }

    override suspend fun updateVideoByTitle(video: Video): Video {
        return assemblyApi.updateVideoByTitle(video.title, VideoResponse(
            title = video.title,
            stockItemId = video.stockItemId,
            text = video.text,
            searchReq = video.searchReq,
            previewUrl = video.previewUrl
        )). let {
            Video(
                title = it.title ?: "",
                stockItemId = it.stockItemId ?: "",
                text = it.text ?: "",
                searchReq = it.searchReq ?: "",
                previewUrl = it.previewUrl ?: ""
            )
        }
    }

    override suspend fun getVideos(): List<Video> {
        return assemblyApi.getVideos().map {
            Video(
                title = it.title ?: "",
                stockItemId = it.stockItemId ?: "",
                text = it.text ?: "",
                searchReq = it.searchReq ?: "",
                previewUrl = it.previewUrl ?: ""
            )
        }
    }

    override suspend fun addVideo(title: String, text: String): Video {
        return assemblyApi.addVideo(VideoResponse(
            title = title,
            text = text
        )).let {
            Video(
                title = it.title ?: "",
                stockItemId = it.stockItemId ?: "",
                text = it.text ?: "",
                searchReq = it.searchReq ?: "",
                previewUrl = it.previewUrl ?: ""
            )
        }
    }

    override suspend fun assembleVideo(title: String): String {

        val result = assemblyApi.assemblyVideo(
            AssemblySendBody(
                title = title
            )
        ).id

        return result
    }

    override fun downloadFile(video: Video) {

        val call = assemblyApi.downloadVideo(video.title)

        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                try {
                    if (response?.isSuccessful == true) {
                        val execute = object : AsyncTask<Void, Void, Void>() {
                            override fun doInBackground(vararg voids: Void): Void? {
                                val writtenToDisk = writeResponseBodyToDisk(response.body(), "${video.title}.mp4")
                                Log.d("download", "file download was a success? $writtenToDisk")
                                Log.d("downloadFile", "success")
                                return null
                            }
                        }.execute()
                    }

                    Log.d("onResponse", "Response came from server")

                } catch (e: IOException) {
                    Log.d("onResponse", "There is an error")
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d("onFailure", t.toString())
            }
        })

    }

    override suspend fun init() {
        assemblyApi.init()
    }

    private fun writeResponseBodyToDisk(body: ResponseBody?, fileName: String): Boolean {
        try {
            // todo change the file location/name according to your needs
            val destinationFile = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName
            )
            Log.e("loading file", destinationFile.path)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body?.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body?.byteStream()
                outputStream = FileOutputStream(destinationFile)

                while (true) {
                    val read = inputStream!!.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream!!.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Log.d("writeResponseBodyToDisk", "file download: $fileSizeDownloaded of $fileSize")
                }

                outputStream!!.flush()

                return true
            } catch (e: IOException) {
                return false
            } finally {
                if (inputStream != null) {
                    inputStream!!.close()
                }

                if (outputStream != null) {
                    outputStream!!.close()
                }
            }
        } catch (e: IOException) {
            return false
        }
    }

}