package com.mesum.showtimes.data

data class SearchMovies(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)