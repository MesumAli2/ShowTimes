package com.mesum.showtimes.data

data class TvResult(
    val page: Int,
    val results: List<Tvs>,
    val total_pages: Int,
    val total_results: Int
)