package com.mesum.showtimes.network

import com.mesum.showtimes.data.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("3/trending/movie/day")
    suspend fun getTrendingMovies(@retrofit2.http.Query("api_key") apiKey: String,@Query("page") page : String): Movies
}