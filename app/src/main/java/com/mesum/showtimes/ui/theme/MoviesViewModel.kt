package com.mesum.showtimes.ui.theme

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mesum.showtimes.data.MovieRepository
import com.mesum.showtimes.data.PageInfo
import com.mesum.showtimes.data.Result
import com.mesum.showtimes.data.ResultX
import com.mesum.showtimes.data.SearchMovies
import com.mesum.showtimes.data.Tvs
import com.mesum.showtimes.data.YoutubeResult
import com.mesum.showtimes.data.search.Movie
import com.mesum.showtimes.data.search.MovieResponse
import com.mesum.showtimes.network.MovieApiService
import com.mesum.showtimes.network.YoutubeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    private val _searchMoviesState: MutableStateFlow<List<ResultX>?> =
        MutableStateFlow(
            listOf()
        )
    val searchMoviesState: StateFlow<List<ResultX>?> = _searchMoviesState.asStateFlow()
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
    private val _stringState = MutableStateFlow<String>("")
    val stringState = _stringState.asStateFlow()
    val stringValue = MutableStateFlow<String>("")


    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    val uiState: StateFlow<Result> = _uiState.asStateFlow()
    private val _youtubeState = MutableLiveData<YoutubeResult>()
    val youtubeState: LiveData<YoutubeResult> = _youtubeState
    private val service: MovieApiService =
        YoutubeApi.retrofitYoutube.create(MovieApiService::class.java)

    private var currentTrendingPage = 1 // Keep track of the current page
    private var upcomingPage = 1 // Keep track of the current page
    private var popularPage = 1 // Keep track of the current page
    private var topRatedPage = 1 // Keep track of the current page
    private var searchPage = 1


    init {
        fetchTrendingMovies()

    }

    fun fetchTrendingMovies() {
        viewModelScope.launch {
            try {
                val result = movieRepository.getTrendingMovies(currentTrendingPage)
                val currentList = trendingMoviesState.value
                val updatedList = currentList + result.results
                currentTrendingPage++
                _trendingMoviesState.value = updatedList
            }catch (e : Exception){
                Log.d("ExceptionIs", e.message.toString())

            }
        }
    }

    fun fetchUpcomingMovies() {
        viewModelScope.launch {
            try {
                val result = movieRepository.getUpcomingMovies(upcomingPage)
                val currentList = upcomingMoviesState.value
                val updatedList = currentList + result.results
                upcomingPage++
                _upcomingMoviesState.value = updatedList
            }catch (e: Exception){
                Log.d("ExceptionIs", e.message.toString())

            }
        }
    }


    fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val result = movieRepository.getPopularMovies(popularPage)
                val currentList = popularMoviesState.value
                val updatedList = currentList + result.results
                popularPage++
                _popularMoviesState.value = updatedList
            }catch (e : Exception){
                Log.d("ExceptionIs", e.message.toString())

            }
        }

    }

    fun fetchTopRatedMovies() {
        viewModelScope.launch {
            try {
                val result = movieRepository.getTopRatedMovies(topRatedPage)
                val currentList = topRatedMoviesState.value
                val updatedList = currentList + result.results
                topRatedPage++
                _topRatedMoviesState.value = updatedList
            }catch (e : Exception){
                Log.d("ExceptionIs", e.message.toString())
            }
        }

    }

    fun searchMovies(query: String) {

        stringValue.value = query
        viewModelScope.launch {
            try {
                val currentKeyword = _stringState.value

                Log.d("SearchMoviesCalled", query.toString())

                val result = movieRepository.searchMovies(
                    currentTrendingPage = searchPage,
                    query = query
                )

                val currentList = _searchMoviesState.value.orEmpty()
                val updatedList = if (query == currentKeyword) {
                    currentList + result.results
                } else {
                    result.results
                }
                Log.d("SearchText", _searchMoviesState.value.toString())

                if (query == currentKeyword) {
                    searchPage++
                } else {
                    searchPage = 1
                }
                _searchMoviesState.value = updatedList
                _stringState.value = query

            } catch (e: Exception) {
                Log.d("MoviesResponse", e.message.toString())
            }
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

        service.getVideo(query, "AIzaSyBrnOFGEMUW4EDzAVQZheB3mSEPxwJdt9g")
            .enqueue(object : Callback<YoutubeResult> {
                override fun onResponse(
                    call: Call<YoutubeResult>,
                    response: Response<YoutubeResult>
                ) {
                    if (response.isSuccessful) {
                        Log.d("VideoFetched", response.body().toString())
                        _youtubeState.value = response.body()
                        response.body().let { _youtubeState.value }
                        _uiState.update {
                            it.copy(videoId = response.body()?.items?.get(0)?.id?.videoId.toString())
                        }
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
    fun onSearchTextChange(text : String){
        _stringState.value =text
    }

}







