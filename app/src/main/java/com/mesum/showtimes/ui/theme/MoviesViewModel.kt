package com.mesum.showtimes.ui.theme

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mesum.showtimes.data.MovieRepository
import com.mesum.showtimes.data.Movies
import com.mesum.showtimes.data.PageInfo
import com.mesum.showtimes.data.Result
import com.mesum.showtimes.data.TvResult
import com.mesum.showtimes.data.Tvs
import com.mesum.showtimes.data.YoutubeResult
import com.mesum.showtimes.network.MovieApiService
import com.mesum.showtimes.network.YoutubeApi
import com.mesum.showtimes.network.YoutubeCaller
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MovieViewModel() : ViewModel() {

    var selectedTabIndex: MutableLiveData<Int> = MutableLiveData(0)
    private val movieRepository: MovieRepository = MovieRepository()
    private val _trendingMoviesState: MutableStateFlow<List<com.mesum.showtimes.data.Result>> =
        MutableStateFlow(
            listOf()
        )
    val trendingMoviesState: StateFlow<List<Result>> = _trendingMoviesState.asStateFlow()

    private val _upcomingMoviesState: MutableStateFlow<List<com.mesum.showtimes.data.Result>> =
        MutableStateFlow(
            listOf()
        )
    val upcomingMoviesState: StateFlow<List<Result>> = _upcomingMoviesState.asStateFlow()

    private val _popularMoviesState: MutableStateFlow<List<com.mesum.showtimes.data.Result>> =
        MutableStateFlow(
            listOf()
        )
    val popularMoviesState: StateFlow<List<Result>> = _popularMoviesState.asStateFlow()


    private val _topRatedMoviesState: MutableStateFlow<List<com.mesum.showtimes.data.Result>> =
        MutableStateFlow(
            listOf()
        )
    val topRatedMoviesState: StateFlow<List<Result>> = _topRatedMoviesState.asStateFlow()

    private val _trendingTvState: MutableStateFlow<List<Tvs>> = MutableStateFlow(
        listOf()
    )
    val trendingTvState: StateFlow<List<Tvs>> = _trendingTvState.asStateFlow()
    var youtubeid: YoutubeResult = YoutubeResult(
        etag = "",
        items = listOf(),
        kind = "",
        nextPageToken = "",
        pageInfo = PageInfo(resultsPerPage = 0, totalResults = 0),
        regionCode = ""

    )
    private val _uiState = MutableStateFlow(
        Result(
            adult = false, backdrop_path = "", genre_ids = listOf(), id = 0,
            media_type = "", original_language = "",
            original_title = "",
            overview = "",
            popularity = 0.0,
            poster_path = "",
            release_date = "",
            title = "",
            video = false,
            vote_average = 0.0,
            vote_count = 0,


        )
    )
    private val _stringState = MutableStateFlow<String>("Hello")
    val stringState = _stringState.asStateFlow()

    val uiState: StateFlow<Result> = _uiState.asStateFlow()
    private val _youtubeState = MutableLiveData<YoutubeResult>()
    val youtubeState: LiveData<YoutubeResult> = _youtubeState
    private val service: MovieApiService =
        YoutubeApi.retrofitYoutube.create(MovieApiService::class.java)

    private var currentTrendingPage = 1 // Keep track of the current page
    private var upcomingPage = 1 // Keep track of the current page
    private var popularPage = 1 // Keep track of the current page
    private var topRatedPage = 1 // Keep track of the current page


    init {
        fetchTrendingMovies()
    }

    fun fetchTrendingMovies() {
        viewModelScope.launch {
            val result = movieRepository.getTrendingMovies(currentTrendingPage)
            val currentList = trendingMoviesState.value
            val updatedList = currentList + result.results
            currentTrendingPage++
            _trendingMoviesState.value = updatedList
        }
    }

    fun fetchUpcomingMovies() {
        viewModelScope.launch {
            val result = movieRepository.getUpcomingMovies(upcomingPage)
            val currentList = upcomingMoviesState.value
            val updatedList = currentList + result.results
            upcomingPage++
            _upcomingMoviesState.value = updatedList
        }
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            val result = movieRepository.getPopularMovies(popularPage)
            val currentList = popularMoviesState.value
            val updatedList = currentList + result.results
            popularPage++
            _popularMoviesState.value = updatedList
        }

    }

    fun fetchTopRatedMovies() {
        viewModelScope.launch {
            val result = movieRepository.getTopRatedMovies(topRatedPage)
            val currentList = topRatedMoviesState.value
            val updatedList = currentList + result.results
            topRatedPage++
            _topRatedMoviesState.value = updatedList
        }

    }

    fun fetchTrendingTvShows() {
        viewModelScope.launch {
            val result = movieRepository.getTrendingTvs(topRatedPage)
            val currentList = trendingTvState.value
            val updatedList = currentList + result.results
            topRatedPage++
            _trendingTvState.value = updatedList
        }

    }


    fun fetchVideo(query: String) {



        service.getVideo(query, "AIzaSyDqX8axTGeNpXRiISTGL7Tya7fjKJDYi4g")
            .enqueue(object : Callback<YoutubeResult> {
                override fun onResponse(
                    call: Call<YoutubeResult>,
                    response: Response<YoutubeResult>
                ) {
                    Log.d("VideoFetched", response.body().toString())
                    _youtubeState.value = response.body()

                    response.body().let { _youtubeState.value }
                    _uiState.update {
                        it.copy(videoId = response.body()?.items?.get(0)?.id?.videoId.toString())
                    }
                }

                override fun onFailure(call: Call<YoutubeResult>, t: Throwable) {
                    Log.d("RequestFailed", t.message.toString())
                }

            })
    }


    fun updatedTrailer(result: Result) {

        Log.d("result", result.toString())
        _uiState.value = result
    }


    fun updateVideo(body: YoutubeResult) {

        _youtubeState.value = body
    }

    fun changeString(word: String){
        _stringState.value = word

    }

}







