package com.mesum.showtimes.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mesum.showtimes.data.MovieRepository
import com.mesum.showtimes.data.Movies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MovieViewModel() : ViewModel() {

     private val movieRepository: MovieRepository = MovieRepository()
    private val _trendingMoviesState: MutableStateFlow<Movies> = MutableStateFlow(Movies(0, listOf(), 0, 0))
    val trendingMoviesState: StateFlow<Movies> = _trendingMoviesState

    init {

        fetchTrendingMovies("72a92a904702629a345d21c3e4fe58ed")
    }
    fun fetchTrendingMovies(apiKey: String) {
        viewModelScope.launch {
            val result = movieRepository.getTrendingMovies(apiKey)
            _trendingMoviesState.value = result
        }
    }
}
