package com.mesum.showtimes.data

import androidx.compose.ui.graphics.painter.Painter

data class Movies(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

sealed class MoviesResult {
    data class Success(val movies: Movies) : MoviesResult()
    data class Error(val errorMessage: String) : MoviesResult()
}

sealed class NetworkStatus {
    object Connected : NetworkStatus()
    object Disconnected : NetworkStatus()
}

data class BottomNavigationItem(val title: String, val selectedIcon: Painter, val unselectedIcon: Painter)
