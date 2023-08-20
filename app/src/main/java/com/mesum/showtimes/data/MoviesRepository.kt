package com.mesum.showtimes.data

import android.util.Log
import com.mesum.showtimes.network.MovieApiService
import com.mesum.showtimes.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException



class MovieRepository {
    private val movieApiService = RetrofitInstance.retrofit.create(MovieApiService::class.java)

    suspend fun getTrendingMovies(apiKey: String): Movies {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getTrendingMovies(apiKey)
                response
            } catch (e : HttpException){
                Movies(0, listOf(),0,0)
            }
        }
    }
}