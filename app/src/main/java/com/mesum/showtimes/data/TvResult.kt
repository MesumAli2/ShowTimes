package com.mesum.showtimes.data

data class TvResult(
    val page: Int,
    val results: List<Tvs>,
    val total_pages: Int,
    val total_results: Int
)

sealed class TvResultApi {
    data class Success(val movies: TvResult) : TvResultApi()
    data class Error(val errorMessage: String) : TvResultApi()
}
