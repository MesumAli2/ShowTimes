package com.mesum.showtimes.network

import android.net.Uri
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

class YoutubeCaller {

    data class VideoElement(val items: List<YoutubeItem>)

    data class YoutubeItem(val id: YoutubeVideoId)

    data class YoutubeVideoId(val videoId: String)

    interface YoutubeApiService {
        @GET("search")
        fun getVideo(
            @Query("q") query: String,
            @Query("key") apiKey: String
        ): Call<VideoElement>
    }

    object YoutubeApi {
        private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: YoutubeApiService = retrofit.create(YoutubeApiService::class.java)
    }

    fun getVideo(query: String = "AIzaSyDqX8axTGeNpXRiISTGL7Tya7fjKJDYi4g", apiKey: String) {
        val encodedQuery = Uri.encode(query, "UTF-8")
        val call = YoutubeApi.service.getVideo(encodedQuery, apiKey)

        call.enqueue(object : Callback<VideoElement> {
            override fun onResponse(call: Call<VideoElement>, response: Response<VideoElement>) {
                if (response.isSuccessful) {
                    val videoItems = response.body()?.items
                    if (!videoItems.isNullOrEmpty()) {
                        Log.d("YoutubeData", videoItems.toString())
                     //   completion(Result.success(videoItems[0]))
                    } else {
                        Log.d("YoutubeData", videoItems.toString())

                        //   completion(Result.failure(IOException("No video items found")))
                    }
                } else {

                    //completion(Result.failure(IOException("Request failed")))
                }
            }

            override fun onFailure(call: Call<VideoElement>, t: Throwable) {
             //   completion(Result.failure(t))
            }
        })
    }
}