package com.mesum.showtimes.data

import android.util.Log
import com.mesum.showtimes.data.search.MovieResponse
import com.mesum.showtimes.network.MovieApiService
import com.mesum.showtimes.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class MovieRepository {
    private val apiKey : String = "72a92a904702629a345d21c3e4fe58ed"
    private val movieApiService = RetrofitInstance.retrofit.create(MovieApiService::class.java)

    suspend fun getTopRatedMovies( currentTrendingPage: Int): MoviesResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getTopRatedMovies(apiKey, page = currentTrendingPage)
                MoviesResult.Success(response)
            } catch (e : HttpException){
                MoviesResult.Error("http error ${e.code()}")

            }catch (e : IOException){
                MoviesResult.Error("Network error ${e.message}")

            }
        }
    }


    suspend fun getTrendingMovies(currentTrendingPage: Int): MoviesResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getTrendingMovies(apiKey, page = currentTrendingPage.toString())
                MoviesResult.Success(response)
            } catch (e: HttpException) {
                MoviesResult.Error("HTTP Error: ${e.code()}")
            } catch (e: IOException) {
                MoviesResult.Error("Network Error: ${e.message}")
            }
        }
    }

    suspend fun getPopularMovies(currentTrendingPage: Int): MoviesResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getPopularMovies(apiKey, page = currentTrendingPage)
                MoviesResult.Success(response)
            } catch (e: HttpException) {
                MoviesResult.Error("HTTP Error: ${e.code()}")
            } catch (e: IOException) {
                MoviesResult.Error("Network Error: ${e.message}")
            }
        }
    }

    suspend fun getUpcomingMovies(currentTrendingPage: Int): MoviesResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getUpcomoingMovies(apiKey, page = currentTrendingPage)
                MoviesResult.Success(response)
            } catch (e: HttpException) {
                MoviesResult.Error("HTTP Error: ${e.code()}")
            } catch (e: IOException) {
                MoviesResult.Error("Network Error: ${e.message}")
            }
        }
    }

    suspend fun getTrendingTvs(currentTrendingPage: Int): TvResultApi {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getTrendingTvs(apiKey, page = currentTrendingPage)
                TvResultApi.Success(response)
            } catch (e: HttpException) {
                TvResultApi.Error("HTTP Error: ${e.code()}")
            } catch (e: IOException) {
                TvResultApi.Error("Network Error: ${e.message}")
            }
        }
    }

    suspend fun searchMovies(currentTrendingPage: Int, query: String): SearchMoviesResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.searchMoviess(apiKey = apiKey, page = currentTrendingPage, query = query)
                SearchMoviesResult.Success(response)
            } catch (e: HttpException) {
                SearchMoviesResult.Error("HTTP Error: ${e.code()}")
            } catch (e: IOException) {
                SearchMoviesResult.Error("Network Error: ${e.message}")
            }
        }
    }


//    suspend fun getVideo(query : String) : YoutubeResult{
//        return withContext(Dispatchers.Main){
//            try {
//                val encodedQuery = Uri.encode(query, "UTF-8")
//                val response = service.getVideo(encodedQuery,"AIzaSyDqX8axTGeNpXRiISTGL7Tya7fjKJDYi4g")
//                response
//            }catch (e : HttpException) {
//                YoutubeResult(etag = "", items = listOf(), kind = "", nextPageToken = "", pageInfo = PageInfo(resultsPerPage = 0, totalResults = 0), regionCode = ""
//                )
//            }
//        }
//
//    }

//    fun getVideo(query: String = "", viewModel: MovieViewModel)  {
//        val encodedQuery = Uri.encode(query, "UTF-8")
//        val call = YoutubeCaller.YoutubeApi.service.getVideo(encodedQuery, "AIzaSyDqX8axTGeNpXRiISTGL7Tya7fjKJDYi4g")
//
//        call.enqueue(object  : Callback<YoutubeResult> {
//            override fun onResponse(call: Call<YoutubeResult>, response: Response<YoutubeResult>) {
//
//                response.body()?.let { viewModel.updateVideo(it)
//                    Log.d("ThisHappen", it.toString())
//
//                }
//            }
//
//            override fun onFailure(call: Call<YoutubeResult>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//
//
//    }

}