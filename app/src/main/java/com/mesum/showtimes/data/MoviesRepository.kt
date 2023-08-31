package com.mesum.showtimes.data

import android.util.Log
import com.mesum.showtimes.data.search.MovieResponse
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
    suspend fun searchMovies(currentTrendingPage: Int, query : String): SearchMovies{
        return withContext(Dispatchers.IO){
            try {
                val response = movieApiService.searchMoviess(apiKey = apiKey, page =currentTrendingPage, query = query )
                Log.d("RespSearch", response.toString())

                response
            }catch (e : HttpException){
                Log.d("RespSearch", "Error response ${e.toString().toString()}")

                SearchMovies(0, listOf(),0,0)
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