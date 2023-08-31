package com.mesum.showtimes.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mesum.showtimes.data.ResultX

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMoviesScreen(
    searchQuery: String, // The search query value
    onSearchQueryChanged: (String) -> Unit, // Lambda function to update the search query
    onMovieClicked: () -> Unit,
    video: (ResultX) -> Unit,
    viewModel: MovieViewModel
) {

    val searchMoviesState by viewModel.searchMoviesState.collectAsState()
    val movieList = searchMoviesState
   val searchText by viewModel.stringState.collectAsState()

    val textSearchObservable by viewModel.stringValue.collectAsState()


    Column(
    ) {
        // Search bar

        TextField(value = textSearchObservable, onValueChange = viewModel::searchMovies, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp))

        // Lazy vertical grid
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(2)
        ) {
            movieList?.size?.let {
                items(it) {
                    if (it == movieList.size - 1) {
                        viewModel.searchMovies(searchText)
                    }
                      MovieGridItemX(movie = movieList[it], onMovieClicked, video)
                }
            }
        }
    }
}

