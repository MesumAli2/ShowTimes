package com.mesum.showtimes.network

import com.mesum.showtimes.data.Movies
import com.mesum.showtimes.data.SearchMovies
import com.mesum.showtimes.data.TvResult
import com.mesum.showtimes.data.Tvs
import com.mesum.showtimes.data.YoutubeResult
import com.mesum.showtimes.data.search.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {


    @GET("3/trending/movie/day")
    suspend fun getTrendingMovies(
        @retrofit2.http.Query("api_key") apiKey: String,
        @Query("page") page: String
    ): Movies

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Movies


    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Movies


    @GET("3/movie/upcoming")
    suspend fun getUpcomoingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Movies

    @GET("/3/trending/tv/day")
    suspend fun getTrendingTvs(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): TvResult

    @GET("/3/search/movie")
   suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("search")
    fun getVideo(
        @Query("q") query: String,
        @Query("key") apiKey: String
    ): Call<YoutubeResult>


    @GET("/3/search/movie")
   suspend fun searchMoviess(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchMovies




}
