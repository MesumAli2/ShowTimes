package com.mesum.showtimes.network

import com.mesum.showtimes.data.Movies
import retrofit2.http.GET

interface MovieApiService {
    @GET("3/trending/movie/day")
    suspend fun getTrendingMovies(@retrofit2.http.Query("api_key") apiKey: String): Movies
}