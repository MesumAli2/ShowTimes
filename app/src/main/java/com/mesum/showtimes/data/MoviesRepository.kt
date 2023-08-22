package com.mesum.showtimes.data

import com.mesum.showtimes.network.MovieApiService
import com.mesum.showtimes.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException



class MovieRepository {
    private val apiKey : String = "72a92a904702629a345d21c3e4fe58ed"
    private val movieApiService = RetrofitInstance.retrofit.create(MovieApiService::class.java)

    suspend fun getTrendingMovies( currentTrendingPage: Int): Movies {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getTrendingMovies(apiKey, page = currentTrendingPage.toString())
                response
            } catch (e : HttpException){
                Movies(0, listOf(),0,0)
            }
        }
    }


    suspend fun getTopRatedMovies( currentTrendingPage: Int): Movies {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getTopRatedMovies(apiKey, page = currentTrendingPage)
                response
            } catch (e : HttpException){
                Movies(0, listOf(),0,0)
            }
        }
    }

    suspend fun getPopularMovies( currentTrendingPage: Int): Movies {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getPopularMovies(apiKey, page = currentTrendingPage)
                response
            } catch (e : HttpException){
                Movies(0, listOf(),0,0)
            }
        }
    }


    suspend fun getUpcomingMovies( currentTrendingPage: Int): Movies {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getUpcomoingMovies(apiKey, page = currentTrendingPage)
                response
            } catch (e : HttpException){
                Movies(0, listOf(),0,0)
            }
        }
    }


    suspend fun getTrendingTvs(currentTrendingPage: Int): TvResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getTrendingTvs(apiKey, page = currentTrendingPage)
                response
            } catch (e : HttpException){
                TvResult(0, listOf(),0,0)
            }
        }
    }


}