package com.mesum.showtimes.ui.theme

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mesum.showtimes.data.MovieRepository
import com.mesum.showtimes.data.Movies
import com.mesum.showtimes.data.Result
import com.mesum.showtimes.data.TvResult
import com.mesum.showtimes.data.Tvs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MovieViewModel() : ViewModel() {

    var selectedTabIndex:MutableLiveData<Int>  = MutableLiveData(0)
    private val movieRepository: MovieRepository = MovieRepository()
    private val _trendingMoviesState: MutableStateFlow<List<com.mesum.showtimes.data.Result>> = MutableStateFlow(
        listOf()
    )
    val trendingMoviesState: StateFlow<List<Result>> = _trendingMoviesState.asStateFlow()

    private val _upcomingMoviesState: MutableStateFlow<List<com.mesum.showtimes.data.Result>> = MutableStateFlow(
        listOf()
    )
    val upcomingMoviesState: StateFlow<List<Result>> = _upcomingMoviesState.asStateFlow()

    private val _popularMoviesState: MutableStateFlow<List<com.mesum.showtimes.data.Result>> = MutableStateFlow(
        listOf()
    )
    val popularMoviesState: StateFlow<List<Result>> = _popularMoviesState.asStateFlow()


    private val _topRatedMoviesState: MutableStateFlow<List<com.mesum.showtimes.data.Result>> = MutableStateFlow(
        listOf()
    )
    val topRatedMoviesState: StateFlow<List<Result>> = _topRatedMoviesState.asStateFlow()

    private val _trendingTvState: MutableStateFlow<List<Tvs>> = MutableStateFlow(
        listOf()
    )
    val trendingTvState: StateFlow<List<Tvs>> = _trendingTvState.asStateFlow()
    private val _uiState = MutableStateFlow(Result(adult = false, backdrop_path = "", genre_ids = listOf(), id = 0,
         media_type = "", original_language= "",
     original_title= "",
     overview = "",
     popularity = 0.0,
     poster_path= "",
     release_date = "",
     title= "",
     video = false,
     vote_average = 0.0,
     vote_count = 0

        ))
    val uiState: StateFlow<Result> = _uiState.asStateFlow()

    private var currentTrendingPage = 1 // Keep track of the current page
    private var upcomingPage = 1 // Keep track of the current page
    private var popularPage = 1 // Keep track of the current page
    private var topRatedPage = 1 // Keep track of the current page




    init {
        fetchTrendingMovies()
    }
    fun fetchTrendingMovies() {
        viewModelScope.launch {
            val result = movieRepository.getTrendingMovies( currentTrendingPage)
            val currentList = trendingMoviesState.value
                val updatedList = currentList + result.results
                currentTrendingPage++
                _trendingMoviesState.value = updatedList
        }
    }

    fun fetchUpcomingMovies() {
        viewModelScope.launch {
            val result = movieRepository.getUpcomingMovies( upcomingPage)
            val currentList = upcomingMoviesState.value
            val updatedList = currentList + result.results
            upcomingPage++
            _upcomingMoviesState.value = updatedList
        }
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            val result = movieRepository.getPopularMovies( popularPage)
            val currentList = popularMoviesState.value
            val updatedList = currentList + result.results
            popularPage++
            _popularMoviesState.value = updatedList
        }

    }

    fun fetchTopRatedMovies() {
        viewModelScope.launch {
            val result = movieRepository.getTopRatedMovies( topRatedPage)
            val currentList = topRatedMoviesState.value
            val updatedList = currentList + result.results
            topRatedPage++
            _topRatedMoviesState.value = updatedList
        }

    }

    fun fetchTrendingTvShows() {
        viewModelScope.launch {
            val result = movieRepository.getTrendingTvs( topRatedPage)
            val currentList = trendingTvState.value
            val updatedList = currentList + result.results
            topRatedPage++
            _trendingTvState.value = updatedList
        }

    }

    fun updatedTrailer(result: Result) {

        Log.d("result", result.toString())
        _uiState.value = result
    }


}
