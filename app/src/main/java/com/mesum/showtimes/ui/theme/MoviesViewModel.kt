package com.mesum.showtimes.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mesum.showtimes.data.MovieRepository
import com.mesum.showtimes.data.Movies
import com.mesum.showtimes.data.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MovieViewModel() : ViewModel() {

     private val movieRepository: MovieRepository = MovieRepository()
    private val _trendingMoviesState: MutableStateFlow<List<com.mesum.showtimes.data.Result>> = MutableStateFlow(
        listOf()
    )
    val trendingMoviesState: StateFlow<List<Result>> = _trendingMoviesState.asStateFlow()
    private var currentTrendingPage = 1 // Keep track of the current page

    init {

        fetchTrendingMovies()
    }
    fun fetchTrendingMovies(apiKey: String = "72a92a904702629a345d21c3e4fe58ed") {

        Log.d("CurrentPage","${currentTrendingPage.toString()}")
        viewModelScope.launch {

            val result = movieRepository.getTrendingMovies(apiKey, currentTrendingPage)

            val currentList = trendingMoviesState.value

                val updatedList = currentList + result.results
                currentTrendingPage++
                _trendingMoviesState.value = updatedList

        }
    }
}
